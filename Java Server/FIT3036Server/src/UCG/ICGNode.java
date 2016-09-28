package UCG;

import java.util.HashMap;

public class ICGNode extends Node{
	private HashMap<String, String> properties;
	public ICGNode(int id, String role, String value, HashMap properties) {
		super(id, role, value);
		//specific properties for the icg nodes
		this.properties = properties;
	}
	
	public HashMap<String, String> getProperties(){
		return this.properties;
	}
	
	public void showProperties(){
		System.out.println(properties.values());
	}

}
