package UCG;

import java.util.HashMap;

public class ICGNode extends Node{
	private transient HashMap<String, String> properties;
	private transient String[] sizeStrings;
	private transient String[] locationStrings;
	private transient Double[] sizes = new Double[3];
	private transient Double[] locations = new Double[3];
	
	public ICGNode(int id, String role, String value, HashMap<String, String> properties) {
		super(id, role, value);
		//specific properties for the icg nodes
		this.properties = properties;
		// list of sizes
		sizeStrings = this.properties.get("size_wdh").split(":");
		for (int i = 0; i < sizeStrings.length; i++) {
			sizes[i] = Double.parseDouble(sizeStrings[i]);
		}
		// list of locations
		locationStrings = this.properties.get("location_xyz").split(":");
		for (int i = 0; i < locationStrings.length; i++) {
			locations[i] = Double.parseDouble(locationStrings[i]);
		}
	}
	
	public HashMap<String, String> getProperties(){
		return this.properties;
	}
	
	public void showProperties(){
		System.out.println(properties.values());
	}

	public double getMinX() {
		return locations[0];
	}
	
	public double getMaxX() {
		return locations[0] + sizes[0];
	}
	
	public double getMinY() {
		return locations[1];
	}
	
	public double getMaxY() {
		return locations[1] + sizes[1];
	}
	
	public double getMinZ() {
		return locations[2];
	}
	
	public double getMaxZ() {
		return locations[2] + sizes[2];
	}
	
	public double getW() {
		return sizes[0];
	}
	
	public double getD() {
		return sizes[1];
	}
	
	public double getH() {
		return sizes[2];
	}
	
	public boolean hasFace(){
		if (this.properties.get("hasFace").equals("Y")){
			return true;
		} else {
			return false;
		}
	}
	
	public double getAngle(){
		return Double.parseDouble(this.properties.get("angle"));
	}
}
