package Relations;

import UCG.ICGNode;

public abstract class Projective {

	abstract double evaluate(ICGNode obj, ICGNode lm, ICGNode speaker);
	
	protected String getQuadrant(double determinant1, double determinant2) {
		// speaker is bottom left
		if ((determinant1 == 0) && (determinant2 > 0)) {
			return "bottomleft";
		}
		
		// speaker is top right
		else if ((determinant1 == 0) && (determinant2 < 0)) {
			return "topright";
		}
		
		// speaker is top left
		else if ((determinant1 < 0) && (determinant2 == 0)) {
			return "topleft";
		}
		
		// speaker is bottom right
		else if ((determinant1 > 0) && (determinant2 == 0)) {
			return "bottomright";
		}
		
		// speaker is bottom
		else if ((determinant1 > 0) && (determinant2 > 0)) {
			return "bottom";
		}
		
		// speaker is right
		else if ((determinant1 > 0) && (determinant2 < 0)) {
			return "right";
		}
		
		// speaker is left
		else if ((determinant1 < 0) && (determinant2 > 0)) {
			return "left";
		}
		
		// speaker is top
		else {
			return "top";
		}
	}
	
	/*
	 * Convert a radian value to degree
	 */
	protected double toDegree(double rad){
		double result = rad * (180 / Math.PI);
		return result;
	}
}
