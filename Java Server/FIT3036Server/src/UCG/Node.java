package UCG;
import java.util.ArrayList;

import com.google.gson.*;

public class Node {
	private String id;
	private String role;
	private String value;
	private ArrayList<Node> children = new ArrayList<Node>();
	
	public Node(String id, String role ,String value, Node child){
		this.id = id;
		this.role = role;
		this.value = value;
		this.children.add(child);
	}
	
	public Node(String id, String role, String value){
		this.id = id;
		this.role = role;
		this.value = value;
	}
	
	public void addChild(Node child){
		this.children.add(child);
	}
	
	public ArrayList<Node> getChild(){
		return this.children;
	}
}
