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
public class SCPResponse {
	private GeoScalePath SmallestCommonPath = new GeoScalePath();
	private List<String> UnknownIdGeoScalePaths = new ArrayList<String>();
	
	/**
	 * Puts the lists into String
	 */
	public String toString() {
		String result = "\n------------------------------\n";
		
		result += "UnknownIdGeoScalePaths : " + UnknownIdGeoScalePaths.toString() + "\n";
		result += "Smallest common Path : " + SmallestCommonPath.toString() + "\n";
		result += "------------------------------\n";
		
		return result;
	}
	
	/**
	 * Constructor
	 * 
	 * @param smallestCommonPath
	 * @param unknownIdGeoScalePaths
	 */
	public SCPResponse(GeoScalePath smallestCommonPath, List<String> unknownIdGeoScalePaths) {
		super();
		SmallestCommonPath = smallestCommonPath;
		UnknownIdGeoScalePaths = unknownIdGeoScalePaths;
	}
	
	/**
	 * Empty Constructor
	 */
	public SCPResponse() {
		super();
	}
	
	/**
	 * Returns the SmallestCommonPath
	 * 
	 * @return SmallestCommonPath
	 */
	public GeoScalePath getSmallestCommonPath() {
		return SmallestCommonPath;
	}
	
	/**
	 * Sets the SmallestCommonPath
	 * 
	 * @param smallestCommonPath
	 */
	public void setSmallestCommonPath(GeoScalePath smallestCommonPath) {
		SmallestCommonPath = smallestCommonPath;
	}
	/**
	 * Returns the list of the ids of the unknown GeoScalePaths
	 * 
	 * @return UnknownIdGeoScalePaths
	 */
	public List<String> getUnknownIdGeoScalePaths() {
		return UnknownIdGeoScalePaths;
	}
	
	/**
	 * Sets the list of the ids of the unknown GeoScalePaths
	 * 
	 * @param unknownIdGeoScalePaths
	 */
	public void setUnknownIdGeoScalePaths(List<String> unknownIdGeoScalePaths) {
		UnknownIdGeoScalePaths = unknownIdGeoScalePaths;
	}

	
}
