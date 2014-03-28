package demoClient;

import jMapViewer.DisplayMap;
import jMapViewer.jMapViewerUtilities;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import xmlElements.GeoScalePath;
import xmlElements.SCPQuery;
import xmlElements.SCPResponse;
import xmlElements.GeoScalePath.Scale;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
* The Graphic3 class is a demonstration class.
* It displays on a map every half second the positions of Amel, Leon, Chantal and Sophie (Amel and Leon 
* are in the same station during the simulation), and the smallest common path between Amel, Leon and 
* Sophie (Chantal is during the simulation out of Toulouse, so we don't considere her).
* It takes all those informations directly from the server.
* Run it after the server is online and the Clients are registred.
*  
* @author Luc Clement
* @author Arnaud Saunier
* 
* @version 1.0 30/01/2014
*/
public class Graphic3 {
	static final String REST_URI = "http://localhost:9999/MyServer/";

	public static void main(String[] args) throws InterruptedException, IOException {
		Client client = Client.create(new DefaultClientConfig());
		URI uri=UriBuilder.fromUri(REST_URI).build();
		WebResource service = client.resource(uri);


		DisplayMap map = new DisplayMap();
		map.setVisible(true);

		String lastSmallestCommonPath = "";

		while(true) {
			// Get the different clients registred on server
			GeoScalePath amel = service.path("server").path("getGeoScalePath").queryParam("name", "Amel").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);
			//GeoScalePath leon = service.path("server").path("getGeoScalePath").queryParam("name", "Leon").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);
			GeoScalePath chantal = service.path("server").path("getGeoScalePath").queryParam("name", "Chantal").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);
			GeoScalePath sophie = service.path("server").path("getGeoScalePath").queryParam("name", "Sophie").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);


			
			// Search the smallest common path between Leon, Amel and Sophie
		    List<String> queryP = new ArrayList<String>();
		    queryP.add("Sophie"); queryP.add("Amel");
		    SCPResponse response = service.path("server").path("smallestCommonPath").accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(SCPResponse.class, new SCPQuery(queryP));
		    
		    GeoScalePath smallestCommonPath = response.getSmallestCommonPath();
		    //System.out.println(response.toString());		    
		    if (smallestCommonPath.getAddress_components().containsKey(Scale.SUBURB)) {
		    	if (!lastSmallestCommonPath.equals("Suburb " + smallestCommonPath.getAddress_components().get(Scale.SUBURB))) {
		    		map.removeArea(lastSmallestCommonPath);
		    		map.drawToulouseSuburb(Integer.parseInt(smallestCommonPath.getAddress_components().get(Scale.SUBURB)), 0, Color.BLACK, Color.cyan);
		    		lastSmallestCommonPath = "Suburb " + smallestCommonPath.getAddress_components().get(Scale.SUBURB);
		    	}
		    } else if (smallestCommonPath.getAddress_components().containsKey(Scale.CITY)) {
		    	if (!lastSmallestCommonPath.equals("Toulouse")) {
		    		map.removeArea(lastSmallestCommonPath);
		    		map.drawArea("Toulouse", Color.black, Color.cyan, jMapViewerUtilities.getArea("(rel[name=\"Toulouse\"][admin_level=8];._;>;);out;"));
		    		lastSmallestCommonPath = "Toulouse";
		    	}
		    } else if (smallestCommonPath.getAddress_components().containsKey(Scale.REGION)) {
		    	if (!lastSmallestCommonPath.equals("Midi-Pyrénées")) {
		    		map.removeArea(lastSmallestCommonPath);
		    		map.drawArea("Midi-Pyrénées", Color.black, Color.cyan, jMapViewerUtilities.getArea("(rel[name=\"Midi-Pyrénées\"];._;>;);out;"));
		    		lastSmallestCommonPath = "Midi-Pyrénées";
		    	}
		    } else {
		    	map.removeArea(lastSmallestCommonPath);
		    }

			// Display the clients on the map
			map.drawPoint("Amel / Leon", Color.yellow, new Double(amel.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(amel.getAddress_components().get(Scale.POINT).split(";")[1]));
			map.drawPoint("Chantal", Color.yellow, new Double(chantal.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(chantal.getAddress_components().get(Scale.POINT).split(";")[1]));
			map.drawPoint("Sophie", Color.yellow, new Double(sophie.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(sophie.getAddress_components().get(Scale.POINT).split(";")[1]));

			// Important : If no wait the program will catch a java.util.ConcurrentModificationException
			Thread.sleep(750);
		}


	}

}
