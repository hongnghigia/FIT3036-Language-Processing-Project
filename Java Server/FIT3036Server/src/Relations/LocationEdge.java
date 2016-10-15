package Relations;

import java.util.HashMap;

import UCG.ICGNode;

public class LocationEdge extends Relation{
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
	
	double distance;
	double distanceZ;
	double eval;
	
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
		topEdge.put("X", square1.get("X") + square1.get("W"));
		topEdge.put("Y", square1.get("Y"));
		topEdge.put("W", square3.get("X") - ( square1.get("X") + square1.get("W") ));
		topEdge.put("D", square1.get("D"));
		topEdge.put("midX", (topEdge.get("X")/2) + topEdge.get("X"));
		topEdge.put("midY", (topEdge.get("Y")/2) + topEdge.get("Y"));
		
		//Bottom Edge
		bottomEdge.put("X", square2.get("X") + square2.get("W"));
		bottomEdge.put("Y", square2.get("Y"));
		bottomEdge.put("W", square4.get("X") - (square1.get("X") + square1.get("W")));
		bottomEdge.put("D", square2.get("D"));
		bottomEdge.put("midX", (bottomEdge.get("X")/2) + bottomEdge.get("X"));
		bottomEdge.put("midY", (bottomEdge.get("Y")/2) + bottomEdge.get("Y"));
		
		//Left Edge
		leftEdge.put("X", square1.get("X"));
		leftEdge.put("Y", square1.get("Y") + square1.get("D"));
		leftEdge.put("W", square1.get("W"));
		leftEdge.put("D", square2.get("Y") - (square1.get("Y") + square1.get("D")));
		leftEdge.put("midX", (leftEdge.get("X")/2) + leftEdge.get("X"));
		leftEdge.put("midY", (leftEdge.get("Y")/2) + leftEdge.get("Y"));
		
		//Right Edge
		rightEdge.put("X", square3.get("X"));
		rightEdge.put("Y", square3.get("Y") + square3.get("D"));
		rightEdge.put("W", square3.get("W"));
		rightEdge.put("D", square4.get("Y") - (square3.get("Y") + square3.get("D")));
		rightEdge.put("midX", (rightEdge.get("X")/2) + rightEdge.get("X"));
		rightEdge.put("midY", (rightEdge.get("Y")/2) + rightEdge.get("Y"));
		
		// Calculating object's middle point
		double objmidX = (obj.getW()/2) + obj.getMinX();
		double objmidY = (obj.getW()/2) + obj.getMinY();
		
		// Check if object is in top edge
		if(objmidX >= topEdge.get("X") && objmidY >= topEdge.get("Y")){
			if(objmidX <= (topEdge.get("X") + topEdge.get("W")) && objmidY <= (topEdge.get("Y") + topEdge.get("D"))){
				// check the position of the object relative to top edge's center
				// if the object is to the left side of center of topEdge and aligned with topEdge's center Y
				// => the distance is from obj center X to topEdge center X
				if(objmidX < topEdge.get("midX")  && objmidY == topEdge.get("Y")){
					distance = topEdge.get("X") - objmidX;
				} else {
					// else if object position is to the left of topEdge center and obj center Y doesn't align
					// forming a "Right Angle Triangle", we can use Pythagoras theorem to solve for distance (the hypotenuse)
					if(objmidX < topEdge.get("midX") && (objmidY < topEdge.get("Y") || objmidY > topEdge.get("Y"))){
						double a = topEdge.get("midX") - objmidX;
						double b = Math.abs(topEdge.get("Y") - objmidY);
						distance = pythagoras(a,b);
						
					}
				}
				// if object center is to the right side of top edge center
				if(objmidX > topEdge.get("midX") && objmidY == topEdge.get("Y")){
					distance = objmidX - topEdge.get("X");
				} else {
					if(objmidX > topEdge.get("midX") && (objmidY > topEdge.get("Y") || objmidY < topEdge.get("Y"))){
						double a = objmidX - topEdge.get("midX");
						double b = Math.abs(objmidY - topEdge.get("Y"));
						distance = pythagoras(a,b);
					}
				}
			}
		}
		// Check if object is in bottom edge
		if(objmidX >= bottomEdge.get("X") && objmidY >= bottomEdge.get("Y")){
			if(objmidX <= (bottomEdge.get("X") + bottomEdge.get("W")) && objmidY <= (bottomEdge.get("Y") + bottomEdge.get("D"))){
				if(objmidX < bottomEdge.get("midX")  && objmidY == bottomEdge.get("Y")){
					distance = bottomEdge.get("X") - objmidX;
				} else {
					// else if object position is to the left of topEdge center and obj center Y doesn't align
					// forming a "Right Angle Triangle", we can use Pythagoras theorem to solve for distance (the hypotenuse)
					if(objmidX < bottomEdge.get("midX") && (objmidY < bottomEdge.get("Y") || objmidY > bottomEdge.get("Y"))){
						double a = bottomEdge.get("midX") - objmidX;
						double b = Math.abs(bottomEdge.get("Y") - objmidY);
						distance = pythagoras(a,b);
						
					}
				}
				// if object center is to the right side of top edge center
				if(objmidX > bottomEdge.get("midX") && objmidY == bottomEdge.get("Y")){
					distance = objmidX - bottomEdge.get("X");
				} else {
					if(objmidX > bottomEdge.get("midX") && (objmidY > bottomEdge.get("Y") || objmidY < bottomEdge.get("Y"))){
						double a = objmidX - bottomEdge.get("midX");
						double b = Math.abs(objmidY - bottomEdge.get("Y"));
						distance = pythagoras(a,b);
					}
				}
			}
		}
		// Check if object is in left edge
		if(objmidX >= leftEdge.get("X") && objmidY >= leftEdge.get("Y")){
			if(objmidX <= (leftEdge.get("X") + leftEdge.get("W")) && objmidY <= (leftEdge.get("Y") + leftEdge.get("D"))){
				if(objmidX < leftEdge.get("midX")  && objmidY == leftEdge.get("Y")){
					distance = leftEdge.get("X") - objmidX;
				} else {
					// else if object position is to the left of topEdge center and obj center Y doesn't align
					// forming a "Right Angle Triangle", we can use Pythagoras theorem to solve for distance (the hypotenuse)
					if(objmidX < leftEdge.get("midX") && (objmidY < leftEdge.get("Y") || objmidY > leftEdge.get("Y"))){
						double a = leftEdge.get("midX") - objmidX;
						double b = Math.abs(leftEdge.get("Y") - objmidY);
						distance = pythagoras(a,b);
						
					}
				}
				// if object center is to the right side of top edge center
				if(objmidX > leftEdge.get("midX") && objmidY == leftEdge.get("Y")){
					distance = objmidX - leftEdge.get("X");
				} else {
					if(objmidX > leftEdge.get("midX") && (objmidY > leftEdge.get("Y") || objmidY < leftEdge.get("Y"))){
						double a = objmidX - leftEdge.get("midX");
						double b = Math.abs(objmidY - leftEdge.get("Y"));
						distance = pythagoras(a,b);
					}
				}
			}
		}
		// Check if object is in right edge
		if(objmidX >= rightEdge.get("X") && objmidY >= rightEdge.get("Y")){
			if(objmidX <= (rightEdge.get("X") + rightEdge.get("W")) && objmidY <= (rightEdge.get("Y") + rightEdge.get("D"))){
				if(objmidX < rightEdge.get("midX")  && objmidY == rightEdge.get("Y")){
					distance = rightEdge.get("X") - objmidX;
				} else {
					// else if object position is to the left of topEdge center and obj center Y doesn't align
					// forming a "Right Angle Triangle", we can use Pythagoras theorem to solve for distance (the hypotenuse)
					if(objmidX < rightEdge.get("midX") && (objmidY < rightEdge.get("Y") || objmidY > rightEdge.get("Y"))){
						double a = rightEdge.get("midX") - objmidX;
						double b = Math.abs(rightEdge.get("Y") - objmidY);
						distance = pythagoras(a,b);
						
					}
				}
				// if object center is to the right side of top edge center
				if(objmidX > rightEdge.get("midX") && objmidY == rightEdge.get("Y")){
					distance = objmidX - rightEdge.get("X");
				} else {
					if(objmidX > rightEdge.get("midX") && (objmidY > rightEdge.get("Y") || objmidY < rightEdge.get("Y"))){
						double a = objmidX - rightEdge.get("midX");
						double b = Math.abs(objmidY - rightEdge.get("Y"));
						distance = pythagoras(a,b);
					}
				}
			}
		}
		if(obj.getMinZ() >= lm.getMaxZ()){
			distanceZ = obj.getMinZ() - lm.getMaxZ();
		} else {
			distanceZ = lm.getMaxZ();
		}
		eval = score(distance, distanceZ);
		return eval;
	}
	
	// calculate the score using the function score = e ^ (-0.5 * D) where D is the distance between the object (center) and the landmark (center)
	private double score(double D, double Z){
		double score = Math.pow(Math.E, (-0.5 + (-0.5 * Z)) * D);
		return score;
	}
	
	// return the hypotenuse value (which is the distance between the landmark and the object using pythagoras theorem
	// 
	private double pythagoras(double a, double b){
		double result = Math.sqrt(a*a + b*b);
		return result;
	}
}
