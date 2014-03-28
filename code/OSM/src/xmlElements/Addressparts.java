package xmlElements;

import javax.xml.bind.annotation.XmlType;

/**
 * MARSHALL TYPE FROM NOMINATIM API.
 * Addressparts is the Java class which represents the geographic hierarchy used
 * in the Nominatim API.
 * This will help when creating the GeoScalePath and matching this hierarchy with the one
 * we chose to use in the GeoScalePath class.
 * 
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlType
public class Addressparts {

	private String house;
	//private String building;
	private String road;
	private String street;
	private String neighbourhood;
	private String suburb;
	private String city;
	private String village;
	private String county;
	private String state;
	private String postcode;
	private String country;
	private String country_code;

	
	/**
 	 * This is the default constructor of the class.
 	 * It constructs a new position with a new set of address components.
 	 *
 	 * @param house
 	 * @param road
 	 * @param street
 	 * @param neighbourhood
 	 * @param suburb
	 * @param city
	 * @param village
	 * @param county
	 * @param state
	 * @param postcode
	 * @param country
	 * @param country_code
 	 * @see GeoScalePath
 	*/
	public Addressparts(String house, String road, String street, String neighbourhood, String suburb,
			String city, String village, String county, String state, String postcode,
			String country, String country_code) {
		super();
		this.house = house;
		this.road = road;
		this.street = street;
		this.neighbourhood = neighbourhood;
		this.suburb = suburb;
		this.city = city;
		this.village = village;
		this.county = county;
		this.state = state;
		this.postcode = postcode;
		this.country = country;
		this.country_code = country_code;
	}

	public Addressparts() {
		super();
	}

	// Getters & Setters

	/**
 	 * Returns the name of the house corresponding to the location.
 	 * 
 	 * @return the house
 	 */
	public String getHouse() {
		return house;
	}

	/**
 	 * Sets the name of the house corresponding to the location.
 	 * 
 	 * @param house
 	 */
	public void setHouse(String house) {
		this.house = house;
	}

	/**
 	 * Returns the name of the road corresponding to the location.
 	 * 
 	 * @return the road
 	 */
	public String getRoad() {
		return road;
	}

	/**
 	 * Sets the name of the road corresponding to the location.
 	 * 
 	 * @param road
 	 */
	public void setRoad(String road) {
		this.road = road;
	}

	/**
 	 * Returns the name of the neigbourhood corresponding to the location.
 	 * 
 	 * @return the neighbourhood
 	 */
	public String getNeighbourhood() {
		return neighbourhood;
	}

	/**
 	 * Sets the name of the neighbourhood corresponding to the location.
 	 * 
 	 * @param neighbourhood
 	 */
	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	/**
 	 * Returns the name of the city corresponding to the location.
 	 * 
 	 * @return the city
 	 */
	public String getCity() {
		return city;
	}

	/**
 	 * Sets the name of the city corresponding to the location.
 	 * 
 	 * @param city
 	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
 	 * Returns the name of the county corresponding to the location.
 	 * 
 	 * @return the county
 	 */	
	public String getCounty() {
		return county;
	}

	/**
 	 * Sets the name of the county corresponding to the location.
 	 * 
 	 * @param county
 	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
 	 * Returns the name of the state corresponding to the location.
 	 * 
 	 * @return the state
 	 */
	public String getState() {
		return state;
	}

	/**
 	 * Sets the name of the state corresponding to the location.
 	 * 
 	 * @param state
 	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
 	 * Returns the postcode corresponding to the location.
 	 * 
 	 * @return the postcode
 	 */
	public String getPostcode() {
		return postcode;
	}

	/**
 	 * Sets the postcode corresponding to the location.
 	 * 
 	 * @param postcode
 	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
 	 * Returns the name of the country corresponding to the location.
 	 * 
 	 * @return the country
 	 */
	public String getCountry() {
		return country;
	}

	/**
 	 * Sets the name of the country corresponding to the location.
 	 * 
 	 * @param country
 	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
 	 * Returns the country code corresponding to the location.
 	 * 
 	 * @return the country code
 	 */
	public String getCountry_code() {
		return country_code;
	}

	/**
 	 * Sets the country code corresponding to the location.
 	 * 
 	 * @param country_code
 	 */
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	
	/**
 	 * Returns the street code corresponding to the location.
 	 * 
 	 * @return the street
 	 */
	public String getStreet() {
		return street;
	}
	
	/**
 	 * Sets the street corresponding to the location.
 	 * 
 	 * @param street
 	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
 	 * Returns the suburb corresponding to the location.
 	 * 
 	 * @return the suburb
 	 */
	public String getSuburb() {
		return suburb;
	}
	
	/**
 	 * Sets the suburb corresponding to the location.
 	 * 
 	 * @param suburb
 	 */
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	/**
 	 * Returns the village corresponding to the location.
 	 * 
 	 * @return the village
 	 */
	public String getVillage() {
		return village;
	}
	
	/**
 	 * Sets the village corresponding to the location.
 	 * 
 	 * @param village
 	 */
	public void setVillage(String village) {
		this.village = village;
	}
	
	
}
