package xmlElements;

import java.io.StringReader;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


/**
 * GeoScalePath is the Java class which creates the scale path of a terminal. 
 * (The scale path is the set of geographical scales defining a point)
 * First, a get request is sent to the Nominatim reverse geocoding service, with the 
 * latitude and longitude and other query parameters to have the search narrowed as the user wants.
 *
 * After receiving the response from the API, the class unmarshalls the result in order to have a result in
 * String.
 *
 * Next step is to construct our GeoScalePath in order to match our geographical hierarchy with the Nominatim one.
 * Finally, the GeoScalePath is composed by the scale (enumeration of our geographical hierarchy).
 *
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlRootElement(name="GeoScalePath")
public class GeoScalePath {
	private HashMap<Scale, String> address_components = new HashMap<Scale, String>();

	/**
 	 * The GeoScalePath method is the construction of the GeoScalePath properly speaking.
 	 * 
 	 *
 	 * @param lat
 	 *		The latitude of the location.
 	 * @param lon
 	 *		The longitude of the location.
 	 * @see Addressparts
 	 * @see reversegeocode
 	*/
	public GeoScalePath(String lat, String lon) {		

		/**
		 * GET Request on Nominatim reverse geocoding service for the parameters lat and lon and unmarshall the result
		*/
		String REST_URI = "http://nominatim.openstreetmap.org/reverse";
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(REST_URI);
		reversegeocode geocodeResponse = service.path("xml").queryParam("lat", lat).queryParam("lon", lon).queryParam("zoom", "18").queryParam("addressdetails", "1").get(reversegeocode.class);

		
		/**
		 * Construct our GeoScalePath using the reversegeocode informations
		 * We use fields of the OpenStreetMap API and match them with our Scales 
		 * (sometimes we use more than 1 OSM field for our scale, in those cases we define priorities)
		*/
		// Construction of the Scale POINT using lat and lon
		address_components.put(Scale.POINT, lat + ";" + lon);
		// Construction of the Scale COUNRY (using the field country_code of OSM)
		if ( geocodeResponse.getAddressparts().getCountry_code() != null && !geocodeResponse.getAddressparts().getCountry_code().equals("") ) {
			address_components.put(Scale.COUNTRY, geocodeResponse.getAddressparts().getCountry_code());
		}
		
		// Construction of the Scale REGION (using the field State of OSM)
		if ( geocodeResponse.getAddressparts().getState() != null &&  !geocodeResponse.getAddressparts().getState().equals("") ) {
			address_components.put(Scale.REGION, geocodeResponse.getAddressparts().getState());
		}
		
		// Construction of the Scale CITY (using the fields Village and City of OSM)
		// If both fields are present, the field City get the priority
		if ( geocodeResponse.getAddressparts().getVillage() != null &&  !geocodeResponse.getAddressparts().getVillage().equals("") ) {
			address_components.put(Scale.CITY, geocodeResponse.getAddressparts().getVillage());
		}
		if ( geocodeResponse.getAddressparts().getCity() != null &&  !geocodeResponse.getAddressparts().getCity().equals("") ) {
			address_components.put(Scale.CITY, geocodeResponse.getAddressparts().getCity());
		}
		
		// Construction of the Scale SUBURB (using the field Suburb of OSM)
		if ( geocodeResponse.getAddressparts().getSuburb() != null &&  !geocodeResponse.getAddressparts().getSuburb().equals("") ) {
			address_components.put(Scale.SUBURB, geocodeResponse.getAddressparts().getSuburb());
		}
		
		// Construction of the Scale NEIGHBOURHOOD (using the field Neighbourhood of OSM)
		if ( geocodeResponse.getAddressparts().getNeighbourhood() != null &&  !geocodeResponse.getAddressparts().getNeighbourhood().equals("") ) {
			address_components.put(Scale.NEIGHBOURHOOD, geocodeResponse.getAddressparts().getNeighbourhood());
		}
		
		// Construction of the Scale STREET (using the fields Street and Road of OSM)
		// If both fields are present, the field Road get the priority
		if ( geocodeResponse.getAddressparts().getStreet() != null &&  !geocodeResponse.getAddressparts().getStreet().equals("") ) {
			address_components.put(Scale.STREET, geocodeResponse.getAddressparts().getStreet());
		}
		if ( geocodeResponse.getAddressparts().getRoad() != null &&  !geocodeResponse.getAddressparts().getRoad().equals("") ) {
			address_components.put(Scale.STREET, geocodeResponse.getAddressparts().getRoad());
		}
		
		// Construction of the Scale BUILDING (using the field House of OSM)
		if ( geocodeResponse.getAddressparts().getHouse() != null &&  !geocodeResponse.getAddressparts().getHouse().equals("") ) {
			address_components.put(Scale.BUILDING, geocodeResponse.getAddressparts().getHouse());
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param address_components
	 */
	public GeoScalePath(HashMap<Scale, String> address_components) {
		this.address_components = address_components;
	}
	
	/**
	 * Constructor
	 * It was used before, but not now.
	 * 
	 * @param marshalledGeoScalePath
	 */
	public GeoScalePath(String marshalledGeoScalePath) {
		try {
			JAXBContext ctx = JAXBContext.newInstance(GeoScalePath.class);
			Unmarshaller u = ctx.createUnmarshaller();
			StringReader reader = new StringReader(marshalledGeoScalePath);
			
			GeoScalePath newGeoScalePath = (GeoScalePath) u.unmarshal(reader);
			this.setAddress_components(newGeoScalePath.getAddress_components());
		} catch (JAXBException e) {
		    System.err.println(e);
		}
	}

	/**
	 * Empty constructor
	 */
	public GeoScalePath() {
		// TODO Auto-generated constructor stub
	}


	/**
 	 * The displayGeoScalePath method simply displays the GeoScalePath in the console.
 	 * The key of each elements in the HashMap is used in the for loop.
 	 *
 	*/	
	public void displayGeoScalePath() {
		System.out.println("Display GeoScalePath :");
		for (Scale key : address_components.keySet())
			System.out.println(key + " - " + address_components.get(key));
		System.out.println("");
	}
	
	/**
	 * Returns the address_components in a string
	 * 
	 * @return result
	 */
	public String toString() {
		String result = "";
		
		for (Scale key : address_components.keySet()) {
			result += "\n" + key + " - " + address_components.get(key);
		}
		
		return result;
	}
	
	/**
	 * The smallestKnownScale returns the smallest known scale of the geoScalePath, or null when address_components is empty.
	 * 
	 * @return the smallest known scale
	 */
	public Scale smallestKnownScale() {
		Scale result = null;
		int currentBiggerOrdinal = -1;
		
		if (address_components != null && address_components.size() > 0)
			for(Scale scale : address_components.keySet())
				if (scale.ordinal() > currentBiggerOrdinal) {
					currentBiggerOrdinal = scale.ordinal();
					result = scale;
				}
		
		return result;
	}
	
	/**
	 * The smallestKnownScaleInstance returns the instance of the smallest known scale of the geoScalePath, or null when address_components is empty.
	 * 
	 * @return the instance of the smallest known scale
	 */
	public String smallestKnownScaleInstance() {
		Scale smallestKnownScale = null;
		int currentBiggerOrdinal = -1;
		
		if (address_components != null && address_components.size() > 0)
			for(Scale scale : address_components.keySet())
				if (scale.ordinal() > currentBiggerOrdinal) {
					currentBiggerOrdinal = scale.ordinal();
					smallestKnownScale = scale;
				}
		
		if (smallestKnownScale != null)
			return address_components.get(smallestKnownScale);
		else
			return null;
	}
	
	/**
	 * Getters and setters
	 * 
	 */
	
	/**
	 * Returns the HashMap address_components.
	 * 
	 * @return address_components
	 */
	public HashMap<Scale, String> getAddress_components() {
		return address_components;
	}

	/**
	 * Sets the elements of the HashMap address_components.
	 * 
	 * @param address_components
	 */
	public void setAddress_components(HashMap<Scale, String> address_components) {
		this.address_components = address_components;
	}




	/**
	 * The Scale enumeration represent our geographical hierarchy.
	 * It is designed to match with the Nominatim API hierarchy.
	 * 
	 * Regarding the POINT(50): it is in a String format : "lat;lon", see String method split(";") to get lat and lon
	*/
	public enum Scale {
		COUNTRY(4),
		REGION(10),
		DEPARTEMENT(12),
		CITY(16),
		SUBURB(20),
		NEIGHBOURHOOD(22),
		STREET(26),
		BUILDING(28),
		POINT(50);
		
		private final int value;
			
		/**
 		 * Sets the value of the scale enumeration.
 		 * 
 		 * @param value
 		*/
		private Scale(int value) {
			this.value = value;
		}

		/**
 		 * Returns the value of the scale enumeration.
 		 * 
 		 * @return the house
 		*/
		public int getValue() {
			return this.value;
		}
	}
}
