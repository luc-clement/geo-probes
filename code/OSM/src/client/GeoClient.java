package client;

import jMapViewer.DisplayMap;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import server.GeoServer;
import xmlElements.GeoScalePath;
import xmlElements.GeoScalePathList;
import xmlElements.GeoScalePathsInAreaQuery;
import xmlElements.SCPQuery;
import xmlElements.SCPResponse;
import xmlElements.GeoScalePath.Scale;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * GeoClient is the Java class which represents a terminal. 
 * This terminal sends a request to the server with the informations gathered thanks to the GeoScalePath class.
 * The user enters the name of the terminal.
 * Then a new GeoScalePath is created. It is displayed and sent to the server.
 * The client can also use the different services proposed by the server.
 * A DisplayMap instance can be called in order to display the wanted informations.
 * 
 *
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
public class GeoClient {
    static final String REST_URI = "http://157.159.110.48:9999/MyServer/"; // URI chosen arbitrary, matches with the one in GeoServer.java
	public static final boolean debug = true; // A test for errors
	
	/**
 	* The main method is where the GeoScalePath is created and sent.
 	* It also display the map for the user.
 	*
 	* @param args The user enters the name he wants to be the one of the terminal.
	 * @throws IOException 
	 * @see GeoScalePath
 	* @see GeoServer
 	* @see DisplayMap
 	*/
	public static void main(String[] args) throws IOException {
		
		// Connexion to server
		Client client = Client.create(new DefaultClientConfig());
	    URI uri=UriBuilder.fromUri(REST_URI).build();
	    WebResource service = client.resource(uri);
		
	    // Send 3 GeoScalePaths to the server
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Cantal").queryParam("lat", "45.093762").queryParam("lon", "2.650595").accept(MediaType.TEXT_PLAIN).get(String.class));
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Dugay Trouin").queryParam("lat", "43.5725").queryParam("lon", "1.460817").accept(MediaType.TEXT_PLAIN).get(String.class));
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Jean Jaures").queryParam("lat", "43.60575").queryParam("lon", "1.448733").accept(MediaType.TEXT_PLAIN).get(String.class));

	    
	    // Test for the smallestCommonPath method from GeoServer
	    List<String> queryP = new ArrayList<String>();
	    queryP.add("Dugay Trouin");
	    queryP.add("Jean Jaures");
	    queryP.add("Cantal");
	    SCPResponse response = service.path("server").path("smallestCommonPath").accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(SCPResponse.class, new SCPQuery(queryP));
	    System.out.println(response.toString());
	    GeoScalePathList GSPList = service.path("server").path("geoScalePathsInArea").accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(GeoScalePathList.class, new GeoScalePathsInAreaQuery(Scale.REGION, "Jean Jaures"));
	    for (GeoScalePath GSP : GSPList.getList()) {
	    	GSP.displayGeoScalePath();
	    }
	    
	    //Test for the DisplayMap class
		DisplayMap temp = new DisplayMap();
		temp.setVisible(true);
		/*
		temp.drawArea("Toulouse", Color.blue, Color.orange, jMapViewerUtilities.getArea("%28rel%5Bname=%27Toulouse%27%5D%5Badmin%5Flevel=%278%27%5D;%2E%97;%3E;%29;out;"));
		temp.drawArea("Quartier 1", Color.blue, Color.blue, jMapViewerUtilities.getArea("%28rel%5Bref=%271%27%5D%5Bsource=%27GrandToulouse%27%5D;%2E%97;%3E;%29;out;"));
		temp.drawArea("Quartier 2", Color.red, Color.red, jMapViewerUtilities.getArea("%28rel%5Bref=%272%27%5D%5Bsource=%27GrandToulouse%27%5D;%2E%97;%3E;%29;out;"));
		temp.drawPoint("Jean Jaures", Color.yellow, 43.60575 , 1.448733);
		*/
		
		//temp.drawToulouseSuburb(1, 0, Color.black, Color.cyan);
		
		for (int i = 1; i<5; ++i)
			temp.drawToulouseSuburb(2, 0, Color.red, Color.red);
		//temp.drawToulouseSuburb(5, 0, Color.black, Color.cyan);

		//Refresh the display
		temp.repaint();

	}
	
}
