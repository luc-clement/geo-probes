package demoClient;

import jMapViewer.DisplayMap;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import xmlElements.GeoScalePath;
import xmlElements.GeoScalePath.Scale;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * The Graphic class is a demonstration class.
 * It displays on a map every half second the positions of Amel, Leon, Chantal and Sophie (Amel and Leon 
 * are in the same station during the simulation), the suburb where Leon is, and a circle
 * area around Chantals position. It takes all those informations directly from the server.
 * Run it after the server is online and the Clients are registred.
 *  
 * @author Luc Clement
 * @author Arnaud Saunier
 * 
 * @version 1.0 30/01/2014
 */

public class Graphic {
	static final String REST_URI = "http://localhost:9999/MyServer/";

	public static void main(String[] args) throws InterruptedException, IOException {
		Client client = Client.create(new DefaultClientConfig());
		URI uri=UriBuilder.fromUri(REST_URI).build();
		WebResource service = client.resource(uri);


		DisplayMap map = new DisplayMap();
		map.setVisible(true);

		int leonSuburb = -1;
		int leonSuburbPrec = -1;

		while(true) {

			// Get the different clients registred on server
			GeoScalePath amel = service.path("server").path("getGeoScalePath").queryParam("name", "Amel").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);
			GeoScalePath leon = service.path("server").path("getGeoScalePath").queryParam("name", "Leon").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);
			GeoScalePath chantal = service.path("server").path("getGeoScalePath").queryParam("name", "Chantal").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);
			GeoScalePath sophie = service.path("server").path("getGeoScalePath").queryParam("name", "Sophie").accept(MediaType.APPLICATION_XML).get(GeoScalePath.class);

			leonSuburb = Integer.parseInt(leon.getAddress_components().get(Scale.SUBURB));
			if (leonSuburbPrec == -1) {
				leonSuburbPrec = leonSuburb;
				map.drawToulouseSuburb(leonSuburb, 0, Color.cyan, Color.cyan);
			}
			
			if (leonSuburb != leonSuburbPrec) {
				map.removeArea("Suburb " + leonSuburbPrec);
				map.drawToulouseSuburb(leonSuburb, 0, Color.cyan, Color.cyan);
			}
			//System.out.println(chantal.getAddress_components().get(Scale.SUBURB));
			//int ChantalS = Integer.parseInt(chantal.getAddress_components().get(Scale.SUBURB));

			// Display the clients on the map
			map.drawPoint("Amel / Leon", Color.yellow, new Double(amel.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(amel.getAddress_components().get(Scale.POINT).split(";")[1]));
			map.drawPoint("Chantal", Color.yellow, new Double(chantal.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(chantal.getAddress_components().get(Scale.POINT).split(";")[1]));
			map.drawPoint("Sophie", Color.yellow, new Double(sophie.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(sophie.getAddress_components().get(Scale.POINT).split(";")[1]));

			map.drawCircle(" ", Color.green, Color.green, new Double(chantal.getAddress_components().get(Scale.POINT).split(";")[0]), new Double(chantal.getAddress_components().get(Scale.POINT).split(";")[1]), 0.02);
			//map.drawToulouseSuburb(ChantalS, 0, Color.red, Color.red);

			leonSuburbPrec = leonSuburb;
			
			// Important : If no wait the program will catch a java.util.ConcurrentModificationException
			Thread.sleep(500);
		}


	}

}
