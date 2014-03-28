package xmlElements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * MARSHALL TYPE FOR THE SERVER API (service smallestCommonPath).
 * A serializable class used as query for the service smallestCommonPath
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlRootElement
public class SCPQuery {
	private List<String> idGeoScalePaths = new ArrayList<String>();

	/**
	 * Empty Constructor
	 */
	public SCPQuery() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param idGeoScalePaths
	 */
	public SCPQuery(List<String> idGeoScalePaths) {
		super();
		this.idGeoScalePaths = idGeoScalePaths;
	}
	
	/**
	 * Returns the list of id of the GeoScalePaths
	 * 
	 * @return idGeoScalePaths
	 */
	public List<String> getIdGeoScalePaths() {
		return idGeoScalePaths;
	}
	
	/**
	 * Sets the id of the GeoScalePaths
	 * 
	 * @param idGeoScalePaths
	 */
	public void setIdGeoScalePaths(List<String> idGeoScalePaths) {
		this.idGeoScalePaths = idGeoScalePaths;
	}
	
}
