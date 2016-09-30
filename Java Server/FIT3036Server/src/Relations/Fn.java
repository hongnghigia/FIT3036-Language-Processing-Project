package Relations;

import java.util.HashMap;

import UCG.ICGNode;

public class Fn extends Relation{
	double boundary = 0.5;
	HashMap<String, Double> square1 = new HashMap<String, Double>();
	HashMap<String, Double> square2 = new HashMap<String, Double>();
	HashMap<String, Double> square3 = new HashMap<String, Double>();
	HashMap<String, Double> square4 = new HashMap<String, Double>();
	
	
	@Override
	double evaluate(ICGNode obj, ICGNode lm) {
		// -----------------------
		// |1				    3|
		// |					 |
		// | 				     |
		// |2				    4|
		// -----------------------
		
		
		// Imagine the landmark in a 2D plane, top view
		//Square 1, top left of the landmark
		//First square XY value, since we doing 2D view --> No Z value is needed.
		square1.put("X", lm.getMinX());
		square1.put("Y", lm.getMinY());	
		//First square Diameter and Width value, again no Height
		square1.put("D", lm.getMinX() + boundary);
		square1.put("W", lm.getMinY() + boundary);
		
		////////////////////////////////////////////
		//Square 2
		square2.put("X", lm.getMinX());
		square2.put("Y", lm.getMaxY() - boundary);
		square2.put("D", lm.getMinX() + boundary);
		square2.put("W", lm.getMaxY() - square2.get("Y"));
		
		//////////////////////////////////////////////
		//Square 3
		square3.put("X", lm.getMaxX() - boundary);
		square3.put("Y", lm.getMinY());
		square3.put("D", lm.getMaxX() - square3.get("X"));
		square3.put("W", lm.getMinY() + boundary);
		
		//////////////////////////////////////////////
		//Square 4
		square4.put("X", lm.getMaxX() - boundary);
		square4.put("Y", lm.getMaxY() - boundary);
		square4.put("D", lm.getMaxX() - square4.get("X"));
		square4.put("W", lm.getMaxY() - square4.get("Y"));
		
		return 0;
	}
	
	
	
}
