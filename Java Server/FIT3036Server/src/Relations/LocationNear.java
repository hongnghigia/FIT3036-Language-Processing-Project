package Relations;

import java.util.Arrays;

import UCG.ICGNode;

public class LocationNear extends Relation {

	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		boolean nearX;
		boolean nearY;
		boolean nearZ;
		double scoreX;
		double scoreY;
		double scoreZ;
		double objectV = obj.getW() * obj.getD() * obj.getH();
		double landmarkV = lm.getW() * lm.getD() * lm.getH();
		ICGNode bigger;
		ICGNode smaller;
		double boundary[] = {0.25, 0.25, 0.25};
		
		// determine which of the two objects is larger
		if (objectV > landmarkV) {
			bigger = obj;
			smaller = lm;
		}
		else {
			bigger = lm;
			smaller = obj;
		}
		
		// if object is large enough, boundary can be extended relative to size of object
		if ((bigger.getW() * 0.25 ) > boundary[0]) {
			boundary[0] = bigger.getW() * 0.25;
		}
		if ((bigger.getD() * 0.25 ) > boundary[1]) {
			boundary[0] = bigger.getW() * 0.25;
		}
		if ((bigger.getH() * 0.25 ) > boundary[2]) {
			boundary[0] = bigger.getW() * 0.25;
		}
		
		// specify values for new boundary box
		double newMinX = bigger.getMinX() - boundary[0];
		double newMaxX = bigger.getMaxX() + boundary[0];
		double newMinY = bigger.getMinY() - boundary[1];
		double newMaxY = bigger.getMaxY() + boundary[1];
		double newMinZ = bigger.getMinZ() - boundary[2];
		double newMaxZ = bigger.getMaxZ() + boundary[2];
		
		// check if near on X plane
		if (smaller.getMinX() < bigger.getMinX()) {
			if (smaller.getMaxX() >= newMinX) {
				nearX = true;
			}
			else {
				nearX = false;
			}
		}
		else {
			if (smaller.getMinX() <= newMaxX) {
				nearX = true;
			}
			else {
				nearX = false;
			}
		}
		
		// check if near on Y plane
		if (smaller.getMinY() < bigger.getMinY()) {
			if (smaller.getMaxY() >= newMinY) {
				nearY = true;
			}
			else {
				nearY = false;
			}
		}
		else {
			if (smaller.getMinY() <= newMaxY) {
				nearY = true;
			}
			else {
				nearY = false;
			}
		}
		
		// check if near on Z plane
		if (smaller.getMinZ() < bigger.getMinZ()) {
			if (smaller.getMaxZ() >= newMinZ) {
				nearZ = true;
			}
			else {
				nearZ = false;
			}
		}
		else {
			if (smaller.getMinZ() <= newMaxZ) {
				nearZ = true;
			}
			else {
				nearZ = false;
			}
		}
		
		// return score of how near the two objects are
		// each 1/3 of the score is for each plane
		if (nearX && nearY && nearZ) {
			
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
			double closestX = tmpX[0];
			if (closestX <= 0) {
				scoreX = 1;
			}
			else {
				scoreX = (boundary[0] - closestX) / boundary[0];
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
			double closestY = tmpY[0];
			if (closestY <= 0) {
				scoreY = 1;
			}
			else {
				scoreY = (boundary[1] - closestY) / boundary[1];
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
			double closestZ = tmpZ[0];
			if (closestZ <= 0) {
				scoreZ = 1;
			}
			else {
				scoreZ = (boundary[2] - closestZ) / boundary[2];
			}
			
			// combine X, Y and Z scores
			//System.out.println(obj.getValue() + " " + lm.getValue() + " " + ((scoreX + scoreY + scoreZ) / 3));
			return (scoreX + scoreY + scoreZ) / 3;
		}
		else {
			return 0.0;
		}
	}
}
