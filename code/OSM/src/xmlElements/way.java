package xmlElements;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * MARSHALL TYPE FOR THE OVERPASS API.
 * This class is needed to process the XML format of the response
 * osm field
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
public class way {
	private List<nd> nd;
	private String id;
	
	/**
 	 * Returns the nd contained in the list of nd (which are nodes hidden) composing a way.
 	 * 
 	 * @return the nd
 	 */
	public List<nd> getNd() {
		return nd;
	}
	public void setNd(List<nd> nd) {
		this.nd = nd;
	}
	
	/**
 	 * Returns the id of the nd.
 	 * 
 	 * @return the id
 	 */
	@XmlAttribute
	public String getId() {
		return id;
	}
	
	/**
 	 * Sets the id of the nd
 	 * 
 	 * @param id The id
 	 */
	public void setId(String id) {
		this.id = id;
	}
	public way() {
		super();
	}
	
	

}
