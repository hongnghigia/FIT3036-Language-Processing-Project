package Relations;

import java.util.Arrays;

import UCG.ICGNode;

public class LocationNear extends Topological {
	private double scoreX;
	private double scoreY;
	private double scoreZ;
	private double sizeW;
	private double sizeD;
	private double sizeH;
	private double xD;
	private double yD;
	private double zD;

	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
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
		
		// scale with larger object if its larger than 1m
		if (bigger.getW() > 1) {
			sizeW = bigger.getW();
		}
		else {
			sizeW = 1;
		}
		
		if (bigger.getD() > 1) {
			sizeD = bigger.getD();
		}
		else {
			sizeD = 1;
		}
		
		if (bigger.getH() > 1) {
			sizeH = bigger.getH();
		}
		else {
			sizeH = 1;
		}
		
		// return score of how near the two objects are
		// each 1/3 of the score is for each plane
			
		// X score
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
		// get distance from smaller to bigger
		Arrays.sort(tmpX);
		xD = tmpX[0];
		if (xD <= 0) {
			scoreX = 1;
		}
		else {
			scoreX = Math.pow(Math.E, (-(1/sizeW) * xD));
		}
		
		// Y score
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
		// get distance from smaller to bigger
		Arrays.sort(tmpY);
		yD = tmpY[0];
		if (yD <= 0) {
			scoreY = 1;
		}
		else {
			scoreY = Math.pow(Math.E, (-(1/sizeD) * yD));
		}
		
		// Z score
		double[] tmpZ = new double[4];
		if (smaller.getMinZ() < bigger.getMinZ()) {
			tmpZ[0] = bigger.getMinZ() - smaller.getMinZ();
			tmpZ[1] = bigger.getMinZ() - smaller.getMaxZ();
			tmpZ[2] = bigger.getMaxZ() - smaller.getMinZ();
			tmpZ[3] = bigger.getMaxZ() - smaller.getMaxZ();
		}
		else {
			tmpZ[0] = smaller.getMinZ() - bigger.getMinZ();
			tmpZ[1] = smaller.getMinZ() - bigger.getMaxZ();
			tmpZ[2] = smaller.getMaxZ() - bigger.getMinZ();
			tmpZ[3] = smaller.getMaxZ() - bigger.getMaxZ();
		}
		// get distance from smaller to bigger
		Arrays.sort(tmpZ);
		zD = tmpZ[0];
		if (zD <= 0) {
			scoreZ = 1;
		}
		else {
			scoreZ = Math.pow(Math.E, (-(1/sizeH) * zD));
		}
		
		// combine x y z scores
		return (scoreX + scoreY + scoreZ) / 3;
	}
}
