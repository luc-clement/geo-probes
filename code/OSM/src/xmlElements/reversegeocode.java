package xmlElements;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * MARSHALL TYPE FROM NOMINATIM API.
 * reversegeocode is the Java class which combines the hierarchy developped in the Addressparts class
 * with a field named "result" used in the unmarshalling in the GeoScalePath class. 
 *
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlRootElement(name="reversegeocode")
public class reversegeocode {

	private Addressparts addressparts;
	private String result;
	
	/**
 	 * This is the default constructor of the class.
 	 * It constructs a instance for the reverse geocoding in the GeoScalePath class.
 	 *
 	 * @param addressparts
 	 * @param result
	 * @see Addressparts
 	 * @see GeoScalePath
 	*/	
 	public reversegeocode(Addressparts addressparts, String result) {
		super();
		this.addressparts = addressparts;
		this.result = result;
	}
	public reversegeocode() {
		super();
	}

	// Getters and setters

	/**
 	 * Returns the result of the reverse geocoding.
 	 * 
 	 * @return the result
 	 */
	public String getResult() {
		return result;
	}

	/**
 	 * Sets the result.
 	 * 
 	 * @param result
 	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
 	 * Returns the address components of the client.
 	 * 
 	 * @return the addressparts
 	 */
	public Addressparts getAddressparts() {
		return addressparts;
	}

	/**
 	 * Sets the addressparts.
 	 * 
 	 * @param addressparts
 	 */
	public void setAddressparts(Addressparts addressparts) {
		this.addressparts = addressparts;
	}
	
}
