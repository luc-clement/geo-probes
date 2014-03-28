package xmlElements;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * MARSHALL TYPE FOR THE OVERPASS API.
 * This class is needed to process the XML format of the response
 * osm field
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
@XmlRootElement(name="osm")
public class osm {

	private List<node> node;
	private List<way> way;
	private relation relation;
	
	
	/**
	 * Test Method to check if the list of nodes obtained is empty or not.
	 */
	public void displayArea() {
		if (node != null)
			for (node currentNode : node) {
				System.out.println(currentNode.getLat() + "  -  " + currentNode.getLon());
				}
		else
			System.out.println("listNodes is empty.");
		
		
	}

	/**
 	 * Returns the list of nodes.
 	 * 
 	 * @return the list of nodes
 	 */
	public List<node> getNode() {
		return node;
	}

	/**
 	 * Sets the list of nodes
 	 * 
 	 * @param node The list nodes
 	 */
	public void setNode(List<node> node) {
		this.node = node;
	}
	
	/**
 	 * Returns the list of ways.
 	 * 
 	 * @return way the list of ways
 	 */
	public List<way> getWay() {
		return way;
	}
	
	/**
 	 * Sets the list of ways.
 	 * 
 	 * @param way The list of ways
 	 */
	public void setWay(List<way> way) {
		this.way = way;
	}

	/**
 	 * Returns the relation contained in the list of relations.
 	 * 
 	 * @return relation The relation
 	 */
	public relation getRelation() {
		return relation;
	}

	/**
 	 * Sets the relations.
 	 * 
 	 * @param relation 
 	 */
	public void setRelation(relation relation) {
		this.relation = relation;
	}

	public osm() {
		super();
	}







}
