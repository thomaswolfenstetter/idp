package graph.model;

<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;




=======
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java

import de.tum.pssif.core.common.PSSIFConstants;
import de.tum.pssif.core.common.PSSIFOption;
import de.tum.pssif.core.common.PSSIFValue;
import de.tum.pssif.core.metamodel.Attribute;
import de.tum.pssif.core.metamodel.DataType;
import de.tum.pssif.core.metamodel.PrimitiveDataType;
import de.tum.pssif.core.model.Edge;


/**
 * A Data container for the Edge from the PSS-IF Model
 * Helps to manage the visualization/modification of the Edge
 * @author Luc
 *
 */
public class MyEdge {
	private MyEdgeType type;
	private IMyNode source;
	private IMyNode destination;
	private Edge edge;
	private boolean visible;
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	private boolean collapseEdge;
	
	/**
	 * Creates a new MyEdge2 Object
=======
	private boolean partnersVisible;
	private boolean collapseEdge;
	
	/**
	 * Creates a new MyEdge Object
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	 * @param edge :  an edge from the PSS-IF Model
	 * @param type :  the type of the edge
	 * @param source : the startpoint of the Edge
	 * @param destination : the endpoint of the Edge
	 */
	public MyEdge(Edge edge, MyEdgeType type, IMyNode source, IMyNode destination ) {
		this.type =type;
		this.source=source;
		this.destination = destination;
		this.edge = edge;
		this.visible = true;
		this.collapseEdge = false;
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
=======
		this.partnersVisible = true;
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	}
	
	/**
	 * Get all the attributes from the PSS-IF Model Edge
	 * @return List with the formated information from the edge. Might be empty
	 */
	private LinkedList<String> calcAttr()
	{
		LinkedList<String> res = new LinkedList<String>();
		
		// get all the attributes
		Collection<Attribute> attr = type.getType().getAttributes();
		
		// loop over all the attributes
		for (Attribute current : attr)
		{
			String attrName = current.getName();
			PSSIFValue value=null;
			
			// check if there is any value available in this concrete edge for the current attribute
			if (current.get(edge)!=null && current.get(edge).isOne())
				value = current.get(edge).getOne();
			
			String attrValue="";
			if (value !=null)
				attrValue = String.valueOf(value.getValue());
			
			String attrUnit = current.getUnit().getName();
			
			String tmp = attrName+" = "+attrValue+" : "+attrUnit;
			
			// check if the attribute was not empty. If it was empyt do not show it
			if (current.get(edge)!=null && attrValue.length()>0)
				res.add(tmp);
		}
		
		return res;
	}
	
	/**
	 * Check if this Edge has an direction
	 * @return true and false
	 */
	public boolean isDirected()
	{
		PSSIFOption<Attribute> tmp =type.getType().getAttribute(PSSIFConstants.BUILTIN_ATTRIBUTE_DIRECTED);
		if (tmp.isOne())
		{
			Attribute edgeDirection = tmp.getOne();
			if (edgeDirection.get(edge).isOne())
				return edgeDirection.get(edge).getOne().asBoolean();
			else
				return false;
		}
		else
			return false;
	}
	/**
	 * Get the name of the Edge Type
	 * @return a HTML String with the name
	 */
	public String getEdgeTypeName()
	{
		String s ;
		
		s= "&lt;&lt;  "+type.getName()+"  &gt;&gt;";
			
		return s;
	}
	
	/**
	 * Get all the Informations about the Edge. Should only be used in the GraphVisualization
	 * @return a HTML String with all the edge informations
	 */
	public String getEdgeInformations()
	{
		String s ;
		
		s= "&lt;&lt;  "+type.getName()+"  &gt;&gt;";
			
		for (String a : calcAttr())
		{
			s+="<br>";
			s+=a;
		}
		
		return s;
	}
	
	public MyEdgeType getEdgeType()
	{
		return type;
	}

	public IMyNode getSourceNode() {
		return source;
	}

	public IMyNode getDestinationNode() {
		return destination;
	}
	
	/**
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	 * Get all the attributes from the PSS-IF Model Edge
	 * @return LinkedList<String> with the formated information from the edge. Might be empty
	 */
	public List<String> getAttributes()
	{
		return calcAttr();
	}
	
	/**
=======
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	 * Get a Mapping from an Attribute name to an Attribute object which contains all the infomations
	 * @return a Mapping from an Attribute name to an Attribute.  Might be empty
	 */
	public HashMap<String, Attribute> getAttributesHashMap()
	{
		 HashMap<String, Attribute> res = new  HashMap<String, Attribute>();
		
		Collection<Attribute> attr = type.getType().getAttributes();
		
		for (Attribute current : attr)
		{
			String attrName = current.getName();
			
			res.put(attrName, current);
		}
		
		return res;
	}
	
	/**
	 * Get the Edge object from the PSSIF Model
	 * @return Edge
	 */
	public Edge getEdge() {
		return edge;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * check if this Edge was introduced by a collapse operation
	 * @return true or false
	 */
	public boolean isCollapseEdge() {
		return collapseEdge;
	}
	
	/**
	 * Set the information if the Edge was introduced by a collapse operation or not
	 */
	public void setCollapseEdge(boolean collapseEdge) {
		this.collapseEdge = collapseEdge;
	}

	
	/**
	 * Update the value of a given attribute
	 * @param attributeName
	 * @param value
	 * @return true if everything went fine, otherwise false
	 */
	public boolean updateAttribute(String attributeName, Object value)
	{		
		PSSIFOption<Attribute> tmp = type.getType().getAttribute(attributeName);
		if (tmp.isOne())
		{
			Attribute attribute = tmp.getOne();
			DataType attrType = attribute.getType();
			
			if (attrType.equals(PrimitiveDataType.BOOLEAN))
			{
				try 
				{
					PSSIFValue res = PrimitiveDataType.BOOLEAN.fromObject(value);
					
					attribute.set(edge, PSSIFOption.one(res));
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
					return false;
				}
			}
			
			if (attrType.equals(PrimitiveDataType.DATE))
			{
				try 
				{
					Date data = parseDate((String) value);
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
					
=======
					System.out.println("Date after parsing "+data);
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
					PSSIFValue res = PrimitiveDataType.DATE.fromObject(data);
					attribute.set(edge, PSSIFOption.one(res));
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
					return false;
				}
			}
			
			if (attrType.equals(PrimitiveDataType.DECIMAL))
			{
				try 
				{
					PSSIFValue res = PrimitiveDataType.DECIMAL.fromObject(value);
					
					attribute.set(edge, PSSIFOption.one(res));
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
					return false;
				}
			}
			
			if (attrType.equals(PrimitiveDataType.INTEGER))
			{
				try 
				{
					PSSIFValue res = PrimitiveDataType.INTEGER.fromObject(value);
					
					attribute.set(edge, PSSIFOption.one(res));
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
					return false;
				}
			}
			
			if (attrType.equals(PrimitiveDataType.STRING))
			{
				try 
				{
					PSSIFValue res = PrimitiveDataType.STRING.fromObject(value);
					
					attribute.set(edge, PSSIFOption.one(res));
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * We only accepts certain date formats. Checks different date formats and returns a date object
	 * @param dateInString
	 * @return a date object, if the given String is not coded in one of the given date formats, null is returned
	 */
	private Date parseDate(String dateInString)
	{
		SimpleDateFormat formatter;
	
		try {
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.parse(dateInString);
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
		} catch (ParseException e) { }
		
		try {
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) { }
		
		try {
			formatter = new SimpleDateFormat("dd/M/yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) { }
		
		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) { }
		
		try {
			formatter = new SimpleDateFormat("dd-M-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) { }
		
		try {
			formatter = new SimpleDateFormat("dd.MM.yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) { }
=======
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("dd.MM.yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		//----------------
		
		try {
			formatter = new SimpleDateFormat("d/M/yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("d-M-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("d.M.yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		//----------------------------------------------
		try {
			formatter = new SimpleDateFormat("d/MM/yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("d-MM-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("d.MM.yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		//----------------------------------------------
		try {
			formatter = new SimpleDateFormat("dd/M/yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		try {
			formatter = new SimpleDateFormat("dd-M-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
		
		try {
			formatter = new SimpleDateFormat("dd.M.yyyy");
			return formatter.parse(dateInString);
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
		} catch (ParseException e) { }
=======
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
		return null;
		
	}
	
	/**
	 * Get a list with all the attributes from this Node
	 * @return A list which contains a list with all the attribute information. Information Order in the list : Name, Value, Unit, Datatype
	 */
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	public LinkedList<LinkedList<String>> getAttributesForTable()
=======
	public LinkedList<LinkedList<String>> getAttributes()
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	{
		LinkedList<LinkedList<String>> attributes = new LinkedList<LinkedList<String>>();
		
		
		Collection<Attribute> attr = type.getType().getAttributes();
		
		for (Attribute current : attr)
		{
			LinkedList<String> currentAttr = new LinkedList<String>();
			
			String attrName = current.getName();
			
			currentAttr.add(attrName);
			
			PSSIFValue value=null;
			
			if (current.get(edge)!=null && current.get(edge).isOne())
				value = current.get(edge).getOne();
			
			String attrValue="";
			if (value !=null)
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
				attrValue = String.valueOf(value.getValue());
=======
			{
				if (((PrimitiveDataType)current.getType()).getName().equals("Date"))
				{
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					attrValue= df.format(value.getValue());
				}
				else
					attrValue = String.valueOf(value.getValue());
			}
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
			
			currentAttr.add(attrValue);
			String attrUnit = current.getUnit().getName();
			currentAttr.add(attrUnit);
			
			currentAttr.add(((PrimitiveDataType)current.getType()).getName());
			
			attributes.add(currentAttr);
		}
		
<<<<<<< HEAD:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
		return attributes;
=======
		return sortAttributes(attributes);
	}

	public boolean isPartnersVisible() {
		return partnersVisible;
	}

	public void setPartnersVisible(boolean partnersVisible) {
		this.partnersVisible = partnersVisible;
	}
	
	private LinkedList<LinkedList<String>> sortAttributes(LinkedList<LinkedList<String>> data)
	{
		Collections.sort(data, new MyAttributeListComparator());
		
		return data;
	}
	
	protected class MyAttributeListComparator implements Comparator<LinkedList<String>>
	{
	  @Override public int compare( LinkedList<String> attr1, LinkedList<String> attr2 )
	  {
	    return attr1.getFirst().compareTo(attr2.getFirst());
	  }
>>>>>>> origin/attempt4:pssif/pssif.viz/src/main/java/graph/model/MyEdge.java
	}
	
}
