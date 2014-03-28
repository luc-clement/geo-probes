package xmlElements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * MARSHALL TYPE FOR THE SERVER API (service geoScalePathsInArea).
 * This class just contains a list of geoScalePaths.
 * It's a serializable class, so JaxB can marshall and unmarshall it for the server service geoScalePathsInArea.
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlRootElement
public class GeoScalePathList {
	private List<GeoScalePath> list = new ArrayList<GeoScalePath>();

	/**
	 * Returns the list of GeoScalePaths.
	 * 
	 * @return list
	 */
	public List<GeoScalePath> getList() {
		return list;
	}

	/**
	 * Sets the list of GeoScalePaths.
	 * 
	 * @param list
	 */
	public void setList(List<GeoScalePath> list) {
		this.list = list;
	}

	/**
	 * Constructor
	 */
	public GeoScalePathList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
