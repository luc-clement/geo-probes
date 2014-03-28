package xmlElements;

import javax.xml.bind.annotation.XmlRootElement;

import xmlElements.GeoScalePath.Scale;

/**
 * MARSHALL TYPE FOR THE SERVER API (service geoScalePathsInArea).
 * This class is a serializable class, used as query for the geoScalePathsInArea service of the server.
 *   
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlRootElement
public class GeoScalePathsInAreaQuery {
	private Scale scale;
	private String id;
	
	/**
	 * Returns the scale.
	 * 
	 * @return scale
	 */
	public Scale getScale() {
		return scale;
	}
	
	/**
	 * Sets the scale.
	 * 
	 * @param scale
	 */
	public void setScale(Scale scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns the id of the GeoScalePath.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id of the GeoScalePath.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Constructor
	 * 
	 * @param scale
	 * @param id
	 */
	public GeoScalePathsInAreaQuery(Scale scale, String id) {
		super();
		this.scale = scale;
		this.id = id;
	}
	/**
	 * Constructor
	 */
	public GeoScalePathsInAreaQuery() {
		super();
	}
	
	

}
