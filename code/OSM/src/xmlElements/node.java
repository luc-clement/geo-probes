package xmlElements;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * MARSHALL TYPE FOR THE OVERPASS API.
 * This class is needed to process the XML format of the response
 * node field
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlType(name="node")
public class node {
	
	private String lat;
	private String lon;	
	private String id;
	
	/**
 	 * Returns the latitude of the node.
 	 * 
 	 * @return the latitude
 	 */
	@XmlAttribute
	public String getLat() {
		return lat;
	}
	/**
 	 * Sets the latitude of the node.
 	 * 
 	 * @param lat The latitude of the node
 	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	/**
 	 * Returns the longitude of the node.
 	 * 
 	 * @return the longitude
 	 */
	@XmlAttribute
	public String getLon() {
		return lon;
	}
	
	/**
 	 * Sets the longitude of the node.
 	 * 
 	 * @param lon The longitude of the node
 	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	/**
 	 * Returns the id of the node (in OSM database).
 	 * 
 	 * @return the id
 	 */
	@XmlAttribute
	public String getId() {
		return id;
	}
	
	/**
 	 * Sets the id of the node.
 	 * 
 	 * @param id The id of the node
 	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public node() {
		super();
	}

	
	
}
