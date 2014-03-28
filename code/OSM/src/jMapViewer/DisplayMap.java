package jMapViewer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

/**
 * DisplayMap is the Java class which displays the map for the user.
 * It creates the frame and the elements created by the user on the map.
 *
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
 */
public class DisplayMap extends JFrame implements JMapViewerEventListener  {

	private static final long serialVersionUID = 1L;

	private JMapViewerTree treeMap = null;

	private JLabel zoomValue=null;

	private JLabel mperpLabelValue = null;

	private Layer layerPoints = new Layer("layerPoints");
	private HashMap<String, MapMarkerDot> points = new HashMap<String, MapMarkerDot>();
	
	private Layer layerAreas = new Layer("LayerAreas");
	private HashMap<String, MapPolygon> areas = new HashMap<String, MapPolygon>();
	
	private Layer layerCircles = new Layer("LayerCircles");
	private HashMap<String, MapMarkerCircle> circles = new HashMap<String, MapMarkerCircle>();


	/**
	 * Constructor
	 */
	public DisplayMap() {
		super("GeoClient");
		setSize(400,400);

		treeMap = new JMapViewerTree("Tree Map");

		/** 
		 * Listen to the map viewer for user operations so components will
		 * receive events and update
		 **/
		map().addJMVListener(this);

		/**
		 * JPanels are defined to create the frame of the application 
		 */
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel panel = new JPanel();
		JPanel panelTop = new JPanel();
		JPanel panelBottom = new JPanel();
		JPanel helpPanel = new JPanel();

		add(panel, BorderLayout.NORTH);
		add(helpPanel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout());
		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(panelBottom, BorderLayout.SOUTH);

		// Key for the user
		JLabel helpLabel = new JLabel("Use right mouse button to move,\n "+ "left double click or mouse wheel to zoom.");
		helpPanel.add(helpLabel);

		/**
		 * These are the different options the user can select or not to show on the map
		 * different elements like a grid or the tree layers.
		 */
		final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
		showMapMarker.setSelected(map().getMapMarkersVisible());
		panelBottom.add(showMapMarker);
		///
		final JCheckBox showTreeLayers = new JCheckBox("Tree Layers visible");
		showTreeLayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				treeMap.setTreeVisible(showTreeLayers.isSelected());
			}
		});
		panelBottom.add(showTreeLayers);

		final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
		showTileGrid.setSelected(map().isTileGridVisible());
		showTileGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map().setTileGridVisible(showTileGrid.isSelected());
			}
		});
		panelBottom.add(showTileGrid);

		final JCheckBox scrollWrapEnabled = new JCheckBox("Scrollwrap enabled");
		scrollWrapEnabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map().setScrollWrapEnabled(scrollWrapEnabled.isSelected());
			}
		});
		panelBottom.add(scrollWrapEnabled);

		add(treeMap, BorderLayout.CENTER);

		/**
		 * Define the center of the map and the level of zoom at the start
		 */
		map().setDisplayPositionByLatLon(43.599391,1.4394,13); 


		// Creation layers
		treeMap.addLayer(layerPoints);
		treeMap.addLayer(layerAreas);
		treeMap.addLayer(layerCircles);

	}        

	/**
	 * Returns the map (which is a JMapViewer object)
	 * 
	 * @return
	 */
	private JMapViewer map(){
		return treeMap.getViewer();
	}

	/**
	 * Method to update zoom parameters
	 */
	private void updateZoomParameters() {
		if (mperpLabelValue!=null)
			mperpLabelValue.setText(String.format("%s",map().getMeterPerPixel()));
		if (zoomValue!=null)
			zoomValue.setText(String.format("%s", map().getZoom()));
	}
	/**
	 * Method to handle zoom options
	 */
	@Override
	public void processCommand(JMVCommandEvent command) {
		if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
				command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
			updateZoomParameters();
		}

	}

	/**
	 * This method permits to draw an area on the map,
	 * and to add it to the layerAreas layer.
	 * 
	 * @param name Name of the layer of the area.
	 * @param borderColor Color of the border line of the area.
	 * @param innerColor Color of the area
	 * @param nodes List of nodes which define the boundaries of the area.
	 */
	public void drawArea(String name,Color borderColor , Color innerColor, List<Coordinate> nodes) {

		Style style = new Style();
		Color myColor = new Color(innerColor.getRed(), innerColor.getGreen(), innerColor.getBlue(), 50);
		Font font = new Font("Calibri", Font.BOLD, 20);

		style.setColor(borderColor);
		style.setBackColor(myColor);
		style.setStroke(new BasicStroke(2));
		style.setFont(font);

		MapPolygon frontieres = new MapPolygonImpl(layerAreas, name, nodes, style);

		//Add the area to the map
		removeArea(name);
		areas.put(name, frontieres);
		map().addMapPolygon(frontieres);
	}
	
	/**
	 * This method removes an area from the map
	 * 
	 * @param name Name of the area to remove
	 */
	public void removeArea(String name) {
		if (areas.containsKey(name)) {
			map().removeMapPolygon(areas.get(name));
			areas.remove(name);
		}
	}
	
	/**
	 * This method permits to draw a circle on the map,
	 * and to add it to the layerCircles layer.
	 * 
	 * @param name Name of the layer of the circle.
	 * @param borderColor Color of the border line of the circle.
	 * @param innerColor Color of the circle.
	 * @param lat the latitude of the center of the circle.
	 * @param lon the longitude of the center o the circle.
	 * @param radius the radius of the circle.
	 */
	public void drawCircle(String name, Color borderColor, Color innerColor, double lat, double lon, double radius) {
		Style style = new Style();
		Color myColor = new Color(innerColor.getRed(), innerColor.getGreen(), innerColor.getBlue(), 50);
		Font font = new Font("Calibri", Font.BOLD, 20);

		style.setColor(borderColor);
		style.setBackColor(myColor);
		style.setStroke(new BasicStroke(2));
		style.setFont(font);
		
		MapMarkerCircle circle = new MapMarkerCircle(layerCircles, name, new Coordinate(lat, lon), radius, null, style);
		
		removeCircle(name);
		circles.put(name, circle);
		map().addMapMarker(circle);
	}

	/**
	 * This method removes a circle from the map
	 * 
	 * @param name the name of the circle to remove
	 */
	public void removeCircle(String name) {
		if (circles.containsKey(name)) {
			map().removeMapMarker(circles.get(name));
			circles.remove(name);
		}
	}

	/**
	 * This method permits to add a dot to the map,
	 * and to add it to the layerPoints layer.
	 * 
	 * @param name Name of the layer and the point.
	 * @param colorPoint Color of the point
	 * @param lat Latitude of the point.
	 * @param lon Longitude of the point.
	 */
	public void drawPoint(String name, Color colorPoint, double lat, double lon){		
		Style style = new Style();
		Font font = new Font("Calibri", Font.BOLD, 10);

		style.setColor(colorPoint);
		style.setBackColor(colorPoint);
		style.setStroke(new BasicStroke(2));
		style.setFont(font);

		Coordinate coord = new Coordinate(lat, lon);
		MapMarkerDot point = new MapMarkerDot(layerPoints, name , coord, style);

		removePoint(name);
		points.put(name, point);
		//Add the point to the map
		map().addMapMarker(point);

	}
	
	/**
	 * This method removes a point from the map
	 * 
	 * @param name The name of the point to remove
	 */
	public void removePoint(String name){
		if (points.containsKey(name)) {
			map().removeMapMarker(points.get(name));
			points.remove(name);
		}
	}
	/**
	 * This method draw a Toulouse suburb on the map (using the OverPass API)
	 * 
	 * @param suburb The number of the suburb to draw 
	 * @param neighbourhood The number of the neighbourhood to draw (0 if you want to draw the whole suburb)
	 * @param borderColor the border color of the area
	 * @param color the inner color of the area 
	 * @throws IOException 
	 */
	public void drawToulouseSuburb(int suburb, int neighbourhood, Color borderColor, Color color) throws IOException {

		if (neighbourhood == 0) {
			drawArea("Suburb "+suburb, borderColor, color, jMapViewerUtilities.getArea("(rel[ref=\""+suburb+"\"][source=\"GrandToulouse\"];._;>;);out;"));
		} else {
			drawArea("Neighbourhood "+suburb+"."+neighbourhood, borderColor, color, jMapViewerUtilities.getArea("(rel[ref=\""+suburb+"."+neighbourhood+"\"][source=\"GrandToulouse\"];._;>;);out;"));
		}
	}


	/**
	 * The main method set the map visible.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		new DisplayMap().setVisible(true);

	}
}

