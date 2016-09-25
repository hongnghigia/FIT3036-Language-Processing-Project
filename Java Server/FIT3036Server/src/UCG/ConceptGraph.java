package UCG;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "CG")
public class ConceptGraph {
	private NodeXML node;
	private List<NodeXML> nodes;
	
	@XmlElementRef()
	public List<NodeXML> getNodes(){
		return nodes;
	}
	public ConceptGraph(){
		nodes = new ArrayList<NodeXML>();
	}
	
	@XmlElement(name = "node")
	public void setNode(NodeXML node){
		this.node = node;
	}
	
	public NodeXML getNode(){
		return node;
	}	
}
