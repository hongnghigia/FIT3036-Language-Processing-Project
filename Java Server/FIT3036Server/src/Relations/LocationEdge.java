package Relations;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import UCG.ICGNode;

public class LocationEdge extends Topological{
	Point2D.Double point1, point2, point3, point4;
	
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
		point1 = new Point2D.Double();
		point2 = new Point2D.Double();
		point3 = new Point2D.Double();
		point4 = new Point2D.Double();
		
		// finding the location of the middle of all the edges of the landmark
		point1.setLocation(lm.getMinX() + (lm.getW()/2), lm.getMinY());
		point2.setLocation(lm.getMinX(), lm.getMinY() + (lm.getD()/2));
		point3.setLocation(lm.getMaxX(), lm.getMinY() + (lm.getD()/2));
		point4.setLocation(lm.getMinX() + (lm.getW()/2), lm.getMaxY());
		
		if (obj.getMinZ() == lm.getMaxZ()){
			multiplier = 1;
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
		} else {
			multiplier = 0.5;
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
		
		if(obj.getMinZ() >= lm.getMaxZ()){
			distanceZ = obj.getMinZ() - lm.getMaxZ();
		} else if (obj.getMaxZ() < lm.getMinZ()){
			distanceZ = lm.getMinZ() - obj.getMaxZ();
		} else {
			distanceZ = lm.getMaxZ();
		}
		
		eval = score(distance, distanceZ, multiplier);
		return eval;
	}
	
	// calculate the score using the function score = e ^ (-0.5 * D) where D is the distance between the object (center) and the landmark (center)
	private double score(double D, double Z, double M){
		double score = Math.pow(Math.E, (-0.5 + (-0.5 * Z)) * D) * M;
		return score;
	}
}
