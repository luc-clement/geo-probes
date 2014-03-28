package jMapViewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import xmlElements.member;
import xmlElements.nd;
import xmlElements.node;
import xmlElements.osm;
import xmlElements.way;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


/**
 * This class' goal is to process the query to Overpass API and to return the response.
 * If a query take too long to process (more than 500 ms), the result of the query will 
 * be saved locally in a file named with a UUID, so we don't have to ask Overpass API 
 * the next time we'll need the response to that query. 
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
 */
public class jMapViewerUtilities {

	/**
	 * This method processes the query and the response from Overpass API.
	 * 
	 * If the query's response is registered locally, it'll just read the local file and return the response.
	 * If not, it'll get the response from Overpass API and registered it locally if it took too long (more than 500 ms).
	 * After getting the response (in XML format) the data is processed in order
	 * to create a list of nodes which belong to the boundary of the area.
	 * 
	 * @param query in format URL
	 * @return A list of nodes corresponding to the area.
	 * @throws IOException 
	 */
	public static List<Coordinate> getArea(String query) throws IOException {
		
		// Check if the query is associated to a file, if it is, just read this file
		String associatedFile = associatedFileToQuery(query);
		if (!associatedFile.equals(""))
			return getCoordinates(associatedFile);
		
		long startTime = System.currentTimeMillis();

		List<Coordinate> result = new ArrayList<Coordinate>();

		String REST_URI = "http://overpass-api.de";
		ClientConfig config = new DefaultClientConfig();
		WebResource service = Client.create(config).resource(REST_URI);

		osm Area = service.path("api").path("interpreter").queryParam("data", query).get(osm.class);

		String lastNodeAdded = "";
		boolean correctOrder = true;
		int temp = 0;

		if ( Area != null && Area.getNode() != null && Area.getWay() != null ) {

			for (member myMember : Area.getRelation().getMember()) {
				String refWay = myMember.getRef();

				//	if (temp == 50)
				//	break;

				for (way myWay : Area.getWay()) {

					// We found our Way, now lets find the nodes
					if (myWay.getId().equals(refWay)) {

						// Here We first have to check if the way is written in the correct order. 			
						if (temp != 0)  { // If it isn't the first node
							if (myWay.getNd().get(0).getRef().equals(lastNodeAdded))
								correctOrder = true;
							else
								correctOrder = false;
						}
						if (correctOrder)
							for (nd myNd : myWay.getNd()) {
								String refNode = myNd.getRef();

								for (node myNode : Area.getNode()) {
									// We found the node to add
									if (myNode.getId().equals(refNode)) {
										result.add( new Coordinate( new Double(myNode.getLat()), new Double(myNode.getLon()) ) );
										lastNodeAdded = myNode.getId();
									}
								}
							} 
						else {
							ListIterator<nd> iterator = myWay.getNd().listIterator(myWay.getNd().size());
							while (iterator.hasPrevious()) {
								nd myNd = iterator.previous();
								String refNode = myNd.getRef();

								for (node myNode : Area.getNode()) {
									// We found the node to add
									if (myNode.getId().equals(refNode)) {
										result.add( new Coordinate( new Double(myNode.getLat()), new Double(myNode.getLon()) ) );
										lastNodeAdded = myNode.getId();
									}
								}
							}
						}


						++temp;
					}

				}

			}
		}

		if (System.currentTimeMillis() - startTime > 500) {
			UUID uuid = new UUID(startTime, System.currentTimeMillis());
			String fileName = uuid.toString();
			
			setCoordinates(fileName, result);
			linkFileToQuery(fileName, query);
		}

		return result;
	}
	
	
	/**
	 * This method write a give list of coordinates into the local file '"txt/overpassQueries/" + fileName'
	 * in the format :		lat;lon
	 * 						lat;lon
	 * 						lat;lon
	 * 						...
	 * 
	 * @param fileName The name of the file we have to write in
	 * @param coordinates the list of coordinates to write in the file
	 * @throws IOException
	 */
	private static void setCoordinates (String fileName, List<Coordinate> coordinates) throws IOException {
		File file = new File("txt/overpassQueries/" + fileName);
		
		FileWriter writer = new FileWriter(file, true);
		
		for (Coordinate coordinate : coordinates) {
			writer.write( Double.toString( coordinate.getLat() ) + ";" );
			writer.write( Double.toString( coordinate.getLon() ) + "\n" );
		}
		
		writer.close();
	}

	/**
	 * This method read the local file '"txt/overpassQueries/" + fileName' and return the associated list of coordinates
	 * 
	 * @param fileName
	 * 		The Name of the file (in local repertory "txt/overpassQueries").
	 * 		The file has to be in the format :		
	 * 						lat;lon
	 * 						lat;lon
	 * 						lat;lon
	 * 						...
	 * @return The list of coordinates written in the local file
	 * @throws IOException
	 */
	private static List<Coordinate> getCoordinates (String fileName) throws IOException {
		File file = new File("txt/overpassQueries/" + fileName);

		List<Coordinate> result = new ArrayList<Coordinate>();
		
		InputStream ips = new FileInputStream(file);
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		
		String ligne;
		while ( (ligne=br.readLine()) != null )
			result.add(new Coordinate( new Double(ligne.split(";")[0]), new Double(ligne.split(";")[1]) ));
		
		br.close();
		
		return result;
	}
	
	/**
	 * This method links a file to a query in the file "listQueries". It writes in this file a new line, format "query<>fileName".
	 * 
	 * @param fileName the name to link to the query.
	 * @param query the query to link to the name.
	 * @throws IOException
	 */
	private static void linkFileToQuery(String fileName, String query) throws IOException {
		File file = new File("txt/overpassQueries/listQueries");
		FileWriter writer = new FileWriter(file, true);
		
		writer.write(query + "<>" + fileName + "\n");
		
		writer.close();
	}
	
	/**
	 * This method checks the file "txt/overpassQueries/listQueries" written in the format "query<>fileName\nquery<>fileName\n...".
	 * If the query already exist in the file, it returns the name of the associated file.
	 * If not, it returns an empty string.
	 * 
	 * @param query The query to search in the file
	 * @return The name of the file associated to the query or an empty string.
	 * @throws IOException
	 */
	private static String associatedFileToQuery(String query) throws IOException {
		String result = "";
		
		File file = new File("txt/overpassQueries/listQueries");
		InputStream ips = new FileInputStream(file);
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		
		String ligne;
		while ( (ligne=br.readLine()) != null )
			if ( ligne.split("<>")[0].equals(query) )
				result = ligne.split("<>")[1];
		
		br.close();
		return result;
	}

}
