package tests;

import jMapViewer.DisplayMap;

import java.awt.Color;
import java.io.IOException;

import demoClient.DemoUtilities;

/**
 * the ClientTest class can be used independently from the server.
 * It's just an example of how to use jMapViewer, ie the methods of an object DisplayMap 
 * and the methods of DemoUtilities (for the Bus Stations in Toulouse) and of jMapViewerUtilities (to draw areas).
 * 
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 * 
 * @version 1.0 30/01/2014
 */
public class ClientTest {

	public static void main(String[] args) throws InterruptedException, IOException {

		DisplayMap temp = new DisplayMap();
		temp.setVisible(true);

		/*
		temp.drawToulouseSuburb(5, 2, Color.black, Color.cyan);
		temp.repaint();

		Thread.sleep(1000);
		Coordinate jardin = DemoUtilities.getCoordinateFromStation("JardinRoyal");
		temp.drawPoint("JardinRoyal", Color.red, jardin.getLat(), jardin.getLon());
		temp.repaint();

		Thread.sleep(1000);
		Coordinate Esquirol = DemoUtilities.getCoordinateFromStation("Esquirol");
		temp.drawPoint("Esquirol", Color.red, Esquirol.getLat(), Esquirol.getLon());
		temp.repaint();

		Thread.sleep(1000);
		temp.removePoint("JardinRoyal");
		temp.repaint();

		Thread.sleep(1000);
		temp.removeArea("Neighbourhood 5.2");
		temp.repaint();
		 */
		
		//temp.drawCircle("CIRCLE TEST", Color.black, Color.green, 43.468546, 1.5354, 0.02);
		
		temp.drawToulouseSuburb(5, 3, Color.red, Color.orange);
		DemoUtilities.drawAllBusStations(temp);
		temp.repaint();
	}

}
