package Relations;

import UCG.ICGNode;

public class LocationNear extends Topological {
	private double xD, yD, zD;
	private double D;
	private double score;

	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		// calculate volumes of object and landmark
		double objectV = obj.getW() * obj.getD() * obj.getH();
		double landmarkV = lm.getW() * lm.getD() * lm.getH();
		ICGNode bigger;
		ICGNode smaller;
		
		// determine which of the two objects is larger
		if (objectV > landmarkV) {
			bigger = obj;
			smaller = lm;
		}
		else {
			bigger = lm;
			smaller = obj;
		}
		
		// return score of how near the two objects are
		// each 1/3 of the score is for each plane
			
		// X distance from object to landmark
		if (smaller.getMinX() > bigger.getMaxX()) {
			xD = smaller.getMinX() - bigger.getMaxX();
		}
		else if (smaller.getMaxX() < bigger.getMinX()) {
			xD = bigger.getMinX() - smaller.getMaxX();
		}
		else {
			xD = 0;
		}
		
		// Y distance from object to landmark
		if (smaller.getMinY() > bigger.getMaxY()) {
			yD = smaller.getMinY() - bigger.getMaxY();
		}
		else if (smaller.getMaxY() < bigger.getMinY()) {
			yD = bigger.getMinY() - smaller.getMaxY();
		}
		else {
			yD = 0;
		}
		
		// Z distance from object to landmark
		if (smaller.getMinZ() > bigger.getMaxZ()) {
			zD = smaller.getMinZ() - bigger.getMaxZ();
		}
		else if (smaller.getMaxZ() < bigger.getMinZ()) {
			zD = bigger.getMinZ() - smaller.getMaxZ();
		}
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
