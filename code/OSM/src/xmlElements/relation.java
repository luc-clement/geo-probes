package xmlElements;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * MARSHALL TYPE FOR THE OVERPASS API.
 * This class is needed to process the XML format of the response
 * relation field 
 * 
 * @author Luc Clement
 * @author Arnaud Saunier
 *
 * @version 1.0 30/01/2014
*/
public class relation {
	
	private List<member> member;
	private String id;
	
	/**
 	 * Returns the member contained in the list of members composing a relation.
 	 * 
 	 * @return the member
 	 */
	public List<member> getMember() {
		return member;
	}
	
	/**
 	 * Sets the list of members
 	 * 
 	 * @param member The list of members
 	 */
	public void setMember(List<member> member) {
		this.member = member;
	}
	
	/**
 	 * Returns the id of the relation.
 	 * 
 	 * @return the id
 	 */
	@XmlAttribute
	public String getId() {
		return id;
	}
	
	/**
 	 * Sets the id of the relation
 	 * 
 	 * @param id The id
 	 */
	public void setId(String id) {
		this.id = id;
	}
	public relation() {
		super();
	}
	
	
		

}
