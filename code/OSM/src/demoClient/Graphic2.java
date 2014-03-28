package demoClient;

import jMapViewer.DisplayMap;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import xmlElements.GeoScalePath;
import xmlElements.GeoScalePathList;
import xmlElements.GeoScalePathsInAreaQuery;
import xmlElements.GeoScalePath.Scale;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/** 
 * The Graphic2 class is a demonstration class.
 * It displays on a map every half second the positions of Leon, and the people in an area around him.
 * It takes all those informations directly from the server.
 * Run it after the server is online and the Clients are registred.
 *  
 * @author Luc Clement
 * @author Arnaud Saunier
 * 
 * @version 1.0 30/01/2014
 */
public class Graphic2 {
	static final String REST_URI = "http://localhost:9999/MyServer/";

	public static void main(String[] args) throws InterruptedException, IOException {
		Client client = Client.create(new DefaultClientConfig());
		URI uri=UriBuilder.fromUri(REST_URI).build();
		WebResource service = client.resource(uri);


		DisplayMap map = new DisplayMap();
		map.setVisible(true);

		int leonSuburb = -1;
		int leonSuburbPrec = -1;
		int i = 0;
		
		while(true) {			
			
			// Get the client Leon
			GeoScalePath leon = service.path("server").path("getGeoScalePath").queryParam("name", "Leon").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);

			leonSuburb = Integer.parseInt(leon.getAddress_components().get(Scale.SUBURB));
			if (leonSuburbPrec == -1) {
				leonSuburbPrec = leonSuburb;
				map.drawToulouseSuburb(leonSuburb, 0, Color.cyan, Color.cyan);
			}
			
			if (leonSuburb != leonSuburbPrec) {
				map.removeArea("Suburb " + leonSuburbPrec);
				map.drawToulouseSuburb(leonSuburb, 0, Color.cyan, Color.cyan);
			}
			
			GeoScalePathList listClientsInArea = service.path("server").path("geoScalePathsInArea").accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(GeoScalePathList.class, new GeoScalePathsInAreaQuery(Scale.SUBURB, "Leon"));

			map.drawPoint("Leon", Color.yellow, new Double(leon.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(leon.getAddress_components().get(Scale.POINT).split(";")[1]));

			// Remove the previous clients
			for (int j = 0; j < i; ++j) {
				map.removePoint("Client " + j);
			}
			i = 0;
			for (GeoScalePath geoScalePath : listClientsInArea.getList()) {
				map.drawPoint("Client " + i, Color.red, new Double(geoScalePath.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(geoScalePath.getAddress_components().get(Scale.POINT).split(";")[1]));
				++i;
			}

			
			leonSuburbPrec = leonSuburb;
			// Important : If no wait the program will catch a java.util.ConcurrentModificationException
			Thread.sleep(500);
		}


	}

}
