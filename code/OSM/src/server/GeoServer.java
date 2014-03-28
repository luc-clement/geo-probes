package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBException;

import client.GeoClient;
import xmlElements.GeoScalePath;
import xmlElements.GeoScalePathList;
import xmlElements.GeoScalePathsInAreaQuery;
import xmlElements.SCPQuery;
import xmlElements.SCPResponse;
import xmlElements.GeoScalePath.Scale;

/**
 * GeoServer is the Java class which lists the terminals connected. 
 * These terminals are the clients and send request to the server.
 * They are saved in a HashMap, and the server propose then some REST services to the clients.
 *
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
 */
@Path("/server")
public class GeoServer {
	private static HashMap<String, GeoScalePath> listGeoScalePaths = new HashMap<String, GeoScalePath>();


	/**
	 * The addGeoScalePath method adds in the HashMap the different terminals which are connected to the server.
	 *
	 * @param name
	 *		The name (i.e. the id) of the terminal connected.
	 * @param lat
	 *		The lattitude of the position of the terminal.
	 * @param lon
	 *		The longitude of the position of the terminal.
	 * @return "Success" after completing the task.
	 * @see GeoScalePath
	 */
	@GET
	@Produces("text/plain")
	@Path("addGeoScalePath")
	public String addGeoScalePath(@QueryParam("name") String name, @QueryParam("lat") String lat, @QueryParam("lon") String lon) {
		GeoScalePath GeoScalePathToAdd = new GeoScalePath(lat, lon);
		System.out.println("Add GeoScalePath \"" + name + "\" : " + lat + " " + lon);
		if (GeoClient.debug) { GeoScalePathToAdd.displayGeoScalePath(); }
		listGeoScalePaths.put(name, GeoScalePathToAdd);
		return "Success";
	}

	/**
	 * The smallestCommonPath method returns the smallest common path between the GeoScalePaths given in the list in arguments
	 * which are in the server Hashmap. It also returns the unknown id given in arguments
	 *
	 * @param idGeoScalePaths
	 * 		The list of the id of the GeoScalePaths concerned by the smallest common path we asked for, using the class SCPQuery (for auto marshalling with JaxB).
	 * @return 
	 *		The smallest common path between the known id, and a list containing the unknown id, using the class SCPResponse (for auto marshalling with JaxB).
	 * @see GeoScalePath
	 * @see SCPQuery
	 * @see SCPResponse
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Produces("application/xml")
	@Consumes("application/xml")
	@Path("smallestCommonPath")
	public SCPResponse smallestCommonPath(SCPQuery idGeoScalePaths) throws JAXBException {

		HashMap<Scale, String> result_address_components = new HashMap<Scale, String>();
		List<String> unknownIdGeoScalePaths = new ArrayList<String>();


		boolean firstRecognizedGeoScalePath = true;

		for (String id : idGeoScalePaths.getIdGeoScalePaths()) {
			// If the id isn't registered, we add the id to the unknownIdGeoScalePaths list
			if (!listGeoScalePaths.containsKey(id))
				unknownIdGeoScalePaths.add(id);

			// If the id is recognized, we check if it's the first one recognized or not
			// If it is the first one, we use it as a reference, if not, we compare it to the reference
			else {
				if (firstRecognizedGeoScalePath) {
					result_address_components = (HashMap<Scale, String>) listGeoScalePaths.get(id).getAddress_components().clone();
					firstRecognizedGeoScalePath = false;
				} else {
					Scale largestScaleToRemove = null;
					for (Scale scale : result_address_components.keySet()) {
						// If the current GeoScalePath doesn't have the same scale, 
						// or has it but with a different value, 
						// we delete this scale in the result.
						if (!listGeoScalePaths.get(id).getAddress_components().containsKey(scale) || 
								(listGeoScalePaths.get(id).getAddress_components().containsKey(scale) && 
										!listGeoScalePaths.get(id).getAddress_components().get(scale).equals(result_address_components.get(scale) ))) {
							if (largestScaleToRemove == null || largestScaleToRemove.ordinal() > scale.ordinal())
								largestScaleToRemove = scale;
						}
					}
					// We determine all the shorter scales that we will remove
					Set<Scale> scaleToRemove = new HashSet<Scale>();
					for (Scale scale : result_address_components.keySet()) {
						if (largestScaleToRemove == null || scale.ordinal() >= largestScaleToRemove.ordinal())
							scaleToRemove.add(scale);
					}
					// And finally we remove all those (non common) scales
					for (Scale scale : scaleToRemove) {
						result_address_components.remove(scale);
					}

				}

			}

		}


		return new SCPResponse(new GeoScalePath(result_address_components), unknownIdGeoScalePaths);
	}


	/**
	 * The geoScalePathsInArea method consumes a GeoScalePathsInAreaQuery, that contains an ID of a geoScalePath (the reference) 
	 * and a scale (the area is the instance of the scale associated to the given geoScalePath) and returns the list of all the 
	 * geoScalePaths in this area.
	 * 
	 * @param query
	 * 		The ID of the reference (geoScalePath) and the scale we use to determine the area 
	 * @return a list of geoScalePaths
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Produces("application/xml")
	@Consumes("application/xml")
	@Path("geoScalePathsInArea")
	public GeoScalePathList geoScalePathsInArea(GeoScalePathsInAreaQuery query) throws JAXBException { 
		String myId = query.getId();
		Scale scale = query.getScale();

		GeoScalePathList result = new GeoScalePathList();
		List<GeoScalePath> myResultList = new ArrayList<GeoScalePath>();

		if (listGeoScalePaths.containsKey(myId)) {
			GeoScalePath myGSP = listGeoScalePaths.get(myId);
			HashMap<Scale, String> myAddress_components = (HashMap<Scale, String>) myGSP.getAddress_components().clone();

			for (String currentId : listGeoScalePaths.keySet()) {
				if (!currentId.equals(myId)) {
					GeoScalePath currentGSP = listGeoScalePaths.get(currentId);
					HashMap<Scale, String> currentAddress_components = (HashMap<Scale, String>) currentGSP.getAddress_components().clone();
					boolean addGSP = true;

					for (Scale currentScale : myAddress_components.keySet()) {
						// If it's a bigger scale
						if (currentScale.ordinal() <= scale.ordinal()) {
							if (!currentAddress_components.containsKey(currentScale) ||
									currentAddress_components.containsKey(currentScale) && !currentAddress_components.get(currentScale).equals(myAddress_components.get(currentScale))) {
								addGSP = false;
							}
						}
					}

					if (addGSP)
						myResultList.add(currentGSP);
				}
			}
		}

		result.setList(myResultList);
		return result;
	}

	/**
	 * The getGeoScalePath method returns the geoScalePath of which we gave the ID in argument. If the ID is unknown, it returns a empty geoScalePath
	 * 
	 * @param name The ID of the geoScalePath.
	 * @return The geoScalePath
	 */
	@GET
	@Produces("application/xml")
	@Path("getGeoScalePath")
	public GeoScalePath getGeoScalePath(@QueryParam("name") String name) {
		if (listGeoScalePaths.containsKey(name)) {
			//System.out.println("getGeoScalePath request : found.\n");
			return listGeoScalePaths.get(name);
		}

		//System.out.println("getGeoScalePath request : not found.\n");
		return new GeoScalePath();
	}
}
