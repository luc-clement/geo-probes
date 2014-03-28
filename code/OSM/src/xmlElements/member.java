package xmlElements;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * MARSHALL TYPE FOR THE OVERPASS API.
 * This class is needed to process the XML format of the response
 * member field
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
public class member {
	
	private String ref;

	/**
 	 * Returns the reference of the member.
 	 * 
 	 * @return the reference
 	 */
	@XmlAttribute
	public String getRef() {
		return ref;
	}
	
	/**
 	 * Sets the reference of the member.
 	 * 
 	 * @param ref The reference of the member
 	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	public member() {
		super();
	}
	
	

}
