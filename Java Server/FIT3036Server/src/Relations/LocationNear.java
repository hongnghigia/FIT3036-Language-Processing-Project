package Relations;

import java.util.Arrays;

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
			
		// X score
		// all distances between X values of object and landmark
		double[] tmpX = new double[4];
		if (smaller.getMinX() < bigger.getMinX()) {
			tmpX[0] = bigger.getMinX() - smaller.getMinX();
			tmpX[1] = bigger.getMinX() - smaller.getMaxX();
			tmpX[2] = bigger.getMaxX() - smaller.getMinX();
			tmpX[3] = bigger.getMaxX() - smaller.getMaxX();
		}
		else {
			tmpX[0] = smaller.getMinX() - bigger.getMinX();
			tmpX[1] = smaller.getMinX() - bigger.getMaxX();
			tmpX[2] = smaller.getMaxX() - bigger.getMinX();
			tmpX[3] = smaller.getMaxX() - bigger.getMaxX();
		}
		// sort distance from smaller to bigger
		Arrays.sort(tmpX);
		// get shortest distance
		xD = tmpX[0];
		
		// Y score
		// all distances between Y values of object and landmark
		double[] tmpY = new double[4];
		if (smaller.getMinY() < bigger.getMinY()) {
			tmpY[0] = bigger.getMinY() - smaller.getMinY();
			tmpY[1] = bigger.getMinY() - smaller.getMaxY();
			tmpY[2] = bigger.getMaxY() - smaller.getMinY();
			tmpY[3] = bigger.getMaxY() - smaller.getMaxY();
		}
		else {
			tmpY[0] = smaller.getMinY() - bigger.getMinY();
			tmpY[1] = smaller.getMinY() - bigger.getMaxY();
			tmpY[2] = smaller.getMaxY() - bigger.getMinY();
			tmpY[3] = smaller.getMaxY() - bigger.getMaxY();
		}
		// sort distance from smaller to bigger
		Arrays.sort(tmpY);
		// get shortest distance
		yD = tmpY[0];
		
		// Z score
		// all distances between
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
