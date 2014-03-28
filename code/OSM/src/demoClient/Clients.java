package demoClient;

import java.net.URI;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * The Clients class is a demonstration class.
 * It sends to the server all the new positions of the protagonists each time you press enter in the console.
 * Run it after the server is online.
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 * 
 * @version 1.0 30/01/2014
 */
public class Clients {
	static final String REST_URI = "http://localhost:9999/MyServer/";

	public static void main(String[] args) {
		Client client = Client.create(new DefaultClientConfig());
	    URI uri=UriBuilder.fromUri(REST_URI).build();
	    WebResource service = client.resource(uri);
	    
	    Scanner sc = new Scanner(System.in);
	    
		newPosition("Esquirol", "Esquirol", "AuzevilleEglise", "Amouroux", service);
		// Sophie part de l'extérieur de la ville au tout début de la simulation
	    service.path("server").path("addGeoScalePath").queryParam("name", "Sophie").queryParam("lat", "43.627409").queryParam("lon", "1.483662").accept(MediaType.TEXT_PLAIN).get(String.class);
	    sc.nextLine();
		newPosition("Esquirol", "Esquirol", "AuzevilleEglise", "Amouroux", service);
		sc.nextLine();
		newPosition("Esquirol", "Esquirol", "AuzevilleEglise", "EgliseBonnefoy", service);
		sc.nextLine();
		newPosition("FrancoisVerdier", "FrancoisVerdier", "CoteauxRamonville", "JeanJaures", service);
		sc.nextLine();
		newPosition("Dumeril", "Dumeril", "Suisse", "FrancoisVerdier", service);
		sc.nextLine();
		newPosition("Aristote", "Aristote", "DeuxOrmeaux", "Dumeril", service);
		sc.nextLine();
		newPosition("EcoleJulesJulien", "EcoleJulesJulien", "Lapeyrade", "Aristote", service);
		sc.nextLine();
		newPosition("FacultedePharmacie", "FacultedePharmacie", "UniversitePaulSabatier", "EcoleJulesJulien", service);
		sc.nextLine();
		newPosition("UniversitePaulSabatier", "UniversitePaulSabatier", "UniversitePaulSabatier", "FacultedePharmacie", service);
		sc.nextLine();
		newPosition("UniversitePaulSabatier", "UniversitePaulSabatier", "UniversitePaulSabatier", "UniversitePaulSabatier", service);


		sc.close();
	}
	
	/**
	 * This method sends the new positions of Amel, Leon, Chantal and Sophie to the server
	 * 
	 * @param amelStation The name of the bus station where Amel is
	 * @param leonStation The name of the bus station where Leon is
	 * @param chantalStation The name of the bus station where Chantal is
	 * @param sophieStation The name of the bus station where Sophie is
	 * @param service The Webresource (server)
	 */
	static private void newPosition(String amelStation, String leonStation, String chantalStation, String sophieStation, WebResource service) {
	    Coordinate amel; String amelLat; String amelLon;
	    Coordinate leon; String leonLat; String leonLon;
	    Coordinate chantal; String chantalLat; String chantalLon;
	    Coordinate sophie; String sophieLat; String sophieLon;
	    
	    
	    amel = DemoUtilities.getCoordinateFromStation(amelStation); amelLat = Double.toString(amel.getLat()); amelLon = Double.toString(amel.getLon());
	    leon = DemoUtilities.getCoordinateFromStation(leonStation); leonLat = Double.toString(leon.getLat()); leonLon = Double.toString(leon.getLon());
	    chantal = DemoUtilities.getCoordinateFromStation(chantalStation); chantalLat = Double.toString(chantal.getLat()); chantalLon = Double.toString(chantal.getLon());
	    sophie = DemoUtilities.getCoordinateFromStation(sophieStation); sophieLat = Double.toString(sophie.getLat()); sophieLon = Double.toString(sophie.getLon());
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Amel").queryParam("lat", amelLat).queryParam("lon", amelLon).accept(MediaType.TEXT_PLAIN).get(String.class));
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Leon").queryParam("lat", leonLat).queryParam("lon", leonLon).accept(MediaType.TEXT_PLAIN).get(String.class));
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Chantal").queryParam("lat", chantalLat).queryParam("lon", chantalLon).accept(MediaType.TEXT_PLAIN).get(String.class));
	    System.out.println("add GeoScalePath in server : " + service.path("server").path("addGeoScalePath").queryParam("name", "Sophie").queryParam("lat", sophieLat).queryParam("lon", sophieLon).accept(MediaType.TEXT_PLAIN).get(String.class));

	}
	
}
