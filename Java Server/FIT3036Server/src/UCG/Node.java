package UCG;
import java.util.ArrayList;

import com.google.gson.*;

public class Node implements Cloneable{
	private int id;
	private String role;
	private String value;
	private ArrayList<Node> children = new ArrayList<Node>();
	
	public Node(int id, String role ,String value, Node child){
		this.id = id;
		this.role = role;
		this.value = value;
		this.children.add(child);
	}
	
	public Node(int id, String role, String value){
		this.id = id;
		this.role = role;
		this.value = value;
	}
	
	public void addChild(Node child){
		this.children.add(child);
	}
	
	public ArrayList<Node> getChildren(){
		return this.children;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public Node getChild(String childVal) {
		Node node = null;
		for (Node n : children) {
			if (n.getValue().equals(childVal)) {
				node = n;
			}
		}
		return node;
	}
	
	public Node getLast() {
		Node node = this;
		while (node.getChildren().size() != 0) {
			node = node.getChildren().get(0);
		}
		return node;
	}
	
	public String getRole(){
		return this.role;
	}
	
	public Node next(){
		return this.children.get(0);
	}
	public Boolean hasChild(){
		if(this.children.size() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public void clearChildren(){
		this.children.clear();
	}
	
	public Node getFirstChild() {
		return this.children.get(0);
	}
	
	public int getId(){
		return this.id;
	}
}
