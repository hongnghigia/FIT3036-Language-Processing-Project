package Relations;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import UCG.ICGNode;

public class LocationEdge extends Topological{
	Point point1, point2, point3, point4;
	
	double dist1, dist2, dist3, dist4;
	double distance;
	double distanceZ;
	double eval;
	
	@Override
	double evaluate(ICGNode obj, ICGNode lm) {
		// -----------------------
		// |          1		     |
		// |					 |
		// |2                   3|
		// | 				     |
		// |          4		     |
		// -----------------------
		
		// Calculating object's middle point
		double objmidX = (obj.getW()/2) + obj.getMinX();
		double objmidY = (obj.getW()/2) + obj.getMinY();
		
		// finding the location of the middle of all the edges of the landmark
		point1 = new Point();
		point2 = new Point();
		point3 = new Point();
		point4 = new Point();
		
		point1.setLocation(lm.getMinX() + (lm.getW()/2), lm.getMinY());
		point2.setLocation(lm.getMinX(), lm.getMinY() + (lm.getD()/2));
		point3.setLocation(lm.getMaxX(), lm.getMinY() + (lm.getD()/2));
		point4.setLocation(lm.getMinX() + (lm.getW()/2), lm.getMaxY());
		
		if (obj.getMinZ() == lm.getMaxZ()){
			if (objmidX >= lm.getMinX() && objmidY >= lm.getMinY()){
				if (objmidX <= lm.getMaxX() && objmidY <= lm.getMaxY()){
					dist1 = Math.hypot(Math.abs(objmidX - point1.getX()), Math.abs(objmidY - point1.getY()));
					dist2 = Math.hypot(Math.abs(objmidX - point2.getX()), Math.abs(objmidY - point2.getY()));
					dist3 = Math.hypot(Math.abs(objmidX - point3.getX()), Math.abs(objmidY - point3.getY()));
					dist4 = Math.hypot(Math.abs(objmidX - point4.getX()), Math.abs(objmidY - point4.getY()));
					
					ArrayList<Double> tmp = new ArrayList<Double>();
					tmp.add(dist1);
					tmp.add(dist2);
					tmp.add(dist3);
					tmp.add(dist4);
					
					Collections.sort(tmp);
					distance = tmp.get(0);
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
