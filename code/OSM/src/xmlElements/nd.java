package xmlElements;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * MARSHALL TYPE FOR THE OVERPASS API.
 * This class is needed to process the XML format of the response
 * nd field
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
public class nd {
	
	private String ref;

	/**
 	 * Returns the reference of the nd (which is a node hidden).
 	 * 
 	 * @return the reference
 	 */
	@XmlAttribute
	public String getRef() {
		return ref;
	}

	/**
 	 * Sets the reference of the nd.
 	 * 
 	 * @param ref The reference of the nd
 	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	public nd() {
		super();
	}
	
	

}
