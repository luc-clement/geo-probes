package jMapViewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 * This class contains a method that read a specific format of coordinates file (from global.mapit.mysociety.org) 
 * and returns a list of coordinates.
 * 
 * It's not use anymore for now (new methods in jMapViewerUtilities).
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
 */
public class CoordinatesReader {

	private static File file = new File("txt/dataFrance.txt");

	public CoordinatesReader(File file) {
		this.setFile(file);

	}
	/**
	 * Read a file (from global.mapit.mysociety.org) and return a list of coordinates
	 * 
	 * @param file File to read
	 * @return The list of coordinates
	 * @throws IOException
	 */
	public static List<Coordinate> readCoordinates (File file) throws IOException {
		String str = "";
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = null;

		List<Double> lonTab = new ArrayList<Double>();
		List<Double> latTab = new ArrayList<Double>();
		List<Coordinate> listFinal = new ArrayList<Coordinate>();


		int compt = 0;

		try {
			scanner = new Scanner((Readable) new BufferedReader(new FileReader(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String lineSeparator = System.getProperty("line.separator");

		try {
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			str = fileContents.toString();
		} finally {
			scanner.close();
		}

		str = str.replaceAll("\\s\\[\\s", "");
		str = str.replaceAll("\\s\\]\\,","\r");
		str = str.replaceAll("\\s\\]","");
		str = str.replaceAll("\\,\\s","\r");

		String[] fullTab = str.split("\r");


		for (int i = 0; i<fullTab.length; ++i) {
			if (compt%2 == 0)
				lonTab.add(new Double(fullTab[compt]));
			else
				latTab.add(new Double(fullTab[compt]));
			++compt;
		}


		for(int i = 0; i < lonTab.size(); i++)
			listFinal.add(new Coordinate(latTab.get(i),lonTab.get(i)));

		return listFinal;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		CoordinatesReader.file = file;
	}


}

