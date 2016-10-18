package Relations;

import java.util.Arrays;

import UCG.ICGNode;

public class LocationCorner extends Topological {
	private double xD, yD, zD;
	private double D;
	private double score;

	@Override
	double evaluate(ICGNode obj, ICGNode lm) {
		// how near a corner on X plane
		Double[] distancesX0 = new Double[2];
		Double[] distancesX1 = new Double[2];
		// distances to min X corner
		distancesX0[0] = Math.abs(obj.getMinX() - lm.getMinX());
		distancesX0[1] = Math.abs(obj.getMaxX() - lm.getMinX());
		// distances to max X corner
		distancesX1[0] = Math.abs(obj.getMinX() - lm.getMaxX());
		distancesX1[1] = Math.abs(obj.getMaxX() - lm.getMaxX());
		// get shortest distance to a corner
		Arrays.sort(distancesX0);
		Arrays.sort(distancesX1);
		if (distancesX0[0] <= distancesX1[0]) {
			xD = distancesX0[0];
		}
		else {
			xD = distancesX1[0];
		}
		
		// how near a corner is on Y plane
		Double[] distancesY0 = new Double[2];
		Double[] distancesY1 = new Double[2];
		// distances to min Y corner
		distancesY0[0] = Math.abs(obj.getMinY() - lm.getMinY());
		distancesY0[1] = Math.abs(obj.getMaxY() - lm.getMinY());
		// distances to max Y corner
		distancesY1[0] = Math.abs(obj.getMinY() - lm.getMaxY());
		distancesY1[1] = Math.abs(obj.getMaxY() - lm.getMaxY());
		// get shortest distance to a corner
		Arrays.sort(distancesY0);
		Arrays.sort(distancesY1);
		if (distancesY0[0] <= distancesY1[0]) {
			yD = distancesY0[0];
		}
		else {
			yD = distancesY1[0];
		}
		
		// how near a corner on Z plane
		Double[] distancesZ0 = new Double[2];
		Double[] distancesZ1 = new Double[2];
		// distances to min Z
		distancesZ0[0] = Math.abs(obj.getMinZ() - lm.getMinZ());
		distancesZ0[1] = Math.abs(obj.getMaxZ() - lm.getMinZ());
		// distances to max Z
		distancesZ1[0] = Math.abs(obj.getMinZ() - lm.getMaxZ());
		distancesZ1[1] = Math.abs(obj.getMaxZ() - lm.getMaxZ());
		// get shortest distance 
		Arrays.sort(distancesZ0);
		Arrays.sort(distancesZ1);
		if (distancesZ0[0] <= distancesZ1[0]) {
			zD = distancesZ0[0];
		}
		else {
			zD = distancesZ1[0];
		}
		
		// distance from object to landmark in 3D plane
		D = Math.sqrt(Math.pow(xD, 2) + Math.pow(yD, 2) + Math.pow(zD, 2));
		
		// calculate score for relation
		score = Math.pow(Math.E, -0.5 * D);
		return score;
	}
}
