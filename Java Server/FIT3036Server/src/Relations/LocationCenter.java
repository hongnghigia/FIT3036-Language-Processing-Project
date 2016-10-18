package Relations;

import UCG.ICGNode;

public class LocationCenter extends Topological {
	
	private double centerXL, centerYL;
	private double xD, yD, zD;
	private double D;
	private double score;

	@Override
	double evaluate(ICGNode obj, ICGNode lm) {
		// center of landmark
		centerXL = lm.getMinX() + (lm.getW() * 0.5);
		centerYL = lm.getMinY() + (lm.getD() * 0.5);
		
		// object is left of landmark's center
		if (obj.getMaxX() < centerXL) {
			xD = centerXL - obj.getMaxX();
		}
		// object is right of landmark's center
		else if (obj.getMinX() > centerXL) {
			xD = obj.getMinX() - centerXL;
		}
		// touching center in X plane
		else {
			xD = 0;
		}
		
		// object is back of landmark's center
		if (obj.getMaxY() < centerYL) {
			yD = centerYL - obj.getMaxY();
		}
		// object is front of landmark's center
		else if (obj.getMinY() > centerYL) {
			yD = obj.getMinY() - centerYL;
		}
		// touching center in Y plane
		else {
			yD = 0;
		}
		
		// object is lower than surface
		if (obj.getMaxZ() < lm.getMaxZ()) {
			zD = lm.getMaxZ() - obj.getMaxZ();
		}
		// object is higher than surface
		else if (obj.getMinZ() > lm.getMaxZ()) {
			zD = obj.getMinZ() - lm.getMaxZ();
		}
		// object is touching surface
		else {
			zD = 0;
		}
		
		// distance from object to landmark in 3D plane
		D = Math.sqrt(Math.pow(xD, 2) + Math.pow(yD, 2) + Math.pow(zD, 2));
		
		// calculate score for relation
		score = Math.pow(Math.E, -0.5 * D);
		return score;
	}
}
