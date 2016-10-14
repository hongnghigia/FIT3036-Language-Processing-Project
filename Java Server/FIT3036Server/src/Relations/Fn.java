package Relations;

import java.util.HashMap;

import UCG.ICGNode;

public class Fn extends Relation{
	double cornerVal = 0.5;
	double edgeW = 0.2;
	
	HashMap<String, Double> square1 = new HashMap<String, Double>();
	HashMap<String, Double> square2 = new HashMap<String, Double>();
	HashMap<String, Double> square3 = new HashMap<String, Double>();
	HashMap<String, Double> square4 = new HashMap<String, Double>();
	HashMap<String, Double> topEdge = new HashMap<String, Double>();
	HashMap<String, Double> bottomEdge = new HashMap<String, Double>();
	HashMap<String, Double> leftEdge = new HashMap<String, Double>();
	HashMap<String, Double> rightEdge = new HashMap<String, Double>();
	
	double atEdge;
	double atCorner;
	
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
		square1.put("W", cornerVal);
		square1.put("D", cornerVal);
		
		////////////////////////////////////////////
		//Square 2
		square2.put("X", lm.getMinX());
		square2.put("Y", lm.getMaxY() - cornerVal);
		square2.put("W", cornerVal);
		square2.put("D", cornerVal);
		
		//////////////////////////////////////////////
		//Square 3
		square3.put("X", lm.getMaxX() - cornerVal);
		square3.put("Y", lm.getMinY());
		square3.put("D", cornerVal);
		square3.put("W", cornerVal);
		
		//////////////////////////////////////////////
		//Square 4
		square4.put("X", lm.getMaxX() - cornerVal);
		square4.put("Y", lm.getMaxY() - cornerVal);
		square4.put("W", cornerVal);
		square4.put("D", cornerVal);
		
		
		//Making edges for the landmark
		//Top Edge
		topEdge.put("X", square1.get("D") + square1.get("X"));
		topEdge.put("Y", square1.get("Y"));
		topEdge.put("W", square3.get("X") - ( square1.get("X") + cornerVal ));
		topEdge.put("D", edgeW);
		
		//Bottom Edge
		bottomEdge.put("X", square2.get("X") + square2.get("W"));
		bottomEdge.put("Y", square2.get("Y"));
		bottomEdge.put("W", square4.get("X") - (square1.get("X") + square1.get("W")));
		bottomEdge.put("D", square2.get("D"));
		
		//Left Edge
		leftEdge.put("X", square1.get("X"));
		leftEdge.put("Y", square1.get("Y") + square1.get("D"));
		leftEdge.put("W", square1.get("W"));
		leftEdge.put("D", square2.get("Y") - (square1.get("Y") + square1.get("D")));
		
		//Right Edge
		rightEdge.put("X", square3.get("X"));
		rightEdge.put("Y", square3.get("Y") + square3.get("D"));
		rightEdge.put("W", square3.get("W"));
		rightEdge.put("D", square4.get("Y") - (square3.get("Y") + square3.get("D")));
		
		//Check if object is inside corner 1
		if (obj.getMinX() >= square1.get("X") && obj.getMaxX() <= square1.get("")){
			if (obj.g)
		}
		//Check if object is inside corner 2
		
		//Check if object is inside corner 3
		
		//Check if object is inside corner 4
		
		return 0;
	}
	
	
	
}
