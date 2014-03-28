package demoClient;

import jMapViewer.DisplayMap;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 * This class helps to display the bus stations from Toulouse in the map, 
 * using the local file "txt/listBusStations.txt" that contains 
 * a list of bus stations from Toulouse and their Coordinates.
 *  
 * @author Luc Clement
 * @author Arnaud Saunier
 * 
 * @version 1.0 30/01/2014
 */
public class DemoUtilities {

	/**
	 * Returns the Coordinate of a given bus station from Toulouse. 
	 * Returns null if the name is unknown (in the local file "txt/listBusStations.txt")
	 * 
	 * @param nameStation The name of the stations (without space)
	 * @return the coordinate of the station or null if this stations is not referenced.
	 */
	public static Coordinate getCoordinateFromStation(String nameStation) {
		File file = new File("txt/listBusStations.txt");

		Coordinate result = null;

		try {
			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){

				if (!ligne.equals("")) {
					String point[] = ligne.split(",");
					if (point[0].equals(nameStation)) {
						result = new Coordinate(new Double(point[1]), new Double(point[2]));
						break;
					}
				}

			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return result;


	}

	/** 
	 * Display all the bus stations referenced in the local file "txt/listBusStations.txt" on the DisplayMap given in argument
	 * 
	 * @param myDisplayMap the DisplayMap on which we have to draw all the bus stations.
	 */
	public static void drawAllBusStations(DisplayMap myDisplayMap) {
		File file = new File("txt/listBusStations.txt");

		try {
			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){

				if (!ligne.equals("")) {
					String point[] = ligne.split(",");
					myDisplayMap.drawPoint(point[0], Color.yellow, new Double(point[1]), new Double(point[2]));
				}

			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
