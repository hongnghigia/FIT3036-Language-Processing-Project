package UCG;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "CG")
public class ConceptGraph {
	private Node node;
	private List<Node> nodes;
	
	@XmlElementRef()
	public List<Node> getNodes(){
		return nodes;
	}
	public ConceptGraph(){
		nodes = new ArrayList<Node>();
	}
	
	@XmlElement(name = "node")
	public void setNode(Node node){
		this.node = node;
	}
	
	public Node getNode(){
		return node;
	}	
}
