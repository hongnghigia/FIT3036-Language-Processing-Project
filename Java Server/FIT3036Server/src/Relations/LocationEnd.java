package Relations;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import UCG.ICGNode;

public class LocationEnd extends Topological{
	double p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y;
	double dist1, dist2, dist3, dist4;
	double distance;
	double multiplier;
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
		
		if (obj.getMinZ() == lm.getMaxZ()){
			multiplier = 1;
			if (objmidX >= lm.getMinX() && objmidY >= lm.getMinY()){
				if (objmidX <= lm.getMaxX() && objmidY <= lm.getMaxY()){
					if (lm.getW() > lm.getD()){
						p2x = lm.getMinX();
						p2y = lm.getMinY() + (lm.getD()/2);
						p3x = lm.getMaxX();
						p3y = lm.getMinY() + (lm.getD()/2);
						
						dist1 = Math.hypot(Math.abs(objmidX - p2x), Math.abs(objmidY - p2y));
						dist2 = Math.hypot(Math.abs(objmidX - p3x), Math.abs(objmidY - p3y));
						
						if (dist1 < dist2){
							distance = dist1;
						} else {
							distance = dist2;
						}
					} else if (lm.getW() < lm.getD()){
						p1x = lm.getMinX() + (lm.getD()/2);
						p1y = lm.getMinY();
						p4x = lm.getMinX() + (lm.getD()/2);
						p4y = lm.getMaxY() + lm.getW();
						
						dist1 = Math.hypot(Math.abs(objmidX - p1x), Math.abs(objmidY - p1y));
						dist2 = Math.hypot(Math.abs(objmidX - p4x), Math.abs(objmidY - p4y));
						
						if (dist1 < dist2){
							distance = dist1;
						} else {
							distance = dist2;
						}
					} else if (lm.getW() == lm.getD()){
						p1x = lm.getMinX() + (lm.getD()/2);
						p1y = lm.getMinY();
						p4x = lm.getMinX() + (lm.getD()/2);
						p4y = lm.getMaxY() + lm.getW();
						p2x = lm.getMinX();
						p2y = lm.getMinY() + (lm.getD()/2);
						p3x = lm.getMaxX();
						p3y = lm.getMinY() + (lm.getD()/2);
						dist1 = Math.hypot(Math.abs(objmidX - p1x), Math.abs(objmidY - p1y));
						dist2 = Math.hypot(Math.abs(objmidX - p2x), Math.abs(objmidY - p2y));
						dist3 = Math.hypot(Math.abs(objmidX - p3x), Math.abs(objmidY - p3y));
						dist4 = Math.hypot(Math.abs(objmidX - p4x), Math.abs(objmidY - p4y));
						
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
		} else {
			multiplier = 0.5;
		}
		
		eval = score(distance, multiplier);
		return eval;
	}
	
	// calculate the score using the function score = e ^ (-0.5 * D) where D is the distance between the object (center) and the landmark (center)
		private double score(double D, double M){
			double score = Math.pow(Math.E, -0.5 * D) * M;
			return score;
		}
}
