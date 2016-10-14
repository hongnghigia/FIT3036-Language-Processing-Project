package Relations;

import java.util.Arrays;

import UCG.ICGNode;

public class LocationCorner extends Relation {
	
	private double scoreX;
	private double scoreY;
	private double scoreZ;

	@Override
	double evaluate(ICGNode obj, ICGNode lm) {
		
		// how near a corner on X plane
		Double[] distancesX0 = new Double[2];
		Double[] distancesX1 = new Double[2];
		// distances to left X
		distancesX0[0] = Math.abs(obj.getMinX() - lm.getMinX());
		distancesX0[1] = Math.abs(obj.getMaxX() - lm.getMinX());
		// distances to right X
		distancesX1[0] = Math.abs(obj.getMinX() - lm.getMaxX());
		distancesX1[1] = Math.abs(obj.getMaxX() - lm.getMaxX());
		// get shortest distance to a corner
		Arrays.sort(distancesX0);
		Arrays.sort(distancesX1);
		if (distancesX0[0] <= distancesX1[0]) {
			Double D = distancesX0[0];
			scoreX = Math.pow(Math.E, (-(1/lm.getW()) * D));
		}
		else {
			Double D = distancesX1[0];
			scoreX = Math.pow(Math.E, (-(1/lm.getW()) * D));
		}
		
		// how near a corner on Y plane
		Double[] distancesY0 = new Double[2];
		Double[] distancesY1 = new Double[2];
		// distances to back Y
		distancesY0[0] = Math.abs(obj.getMinY() - lm.getMinY());
		distancesY0[1] = Math.abs(obj.getMaxY() - lm.getMinY());
		// distances to front Y
		distancesY1[0] = Math.abs(obj.getMinY() - lm.getMaxY());
		distancesY1[1] = Math.abs(obj.getMaxY() - lm.getMaxY());
		// get shortest distance to a corner
		Arrays.sort(distancesY0);
		Arrays.sort(distancesY1);
		if (distancesY0[0] <= distancesY1[0]) {
			Double D = distancesY0[0];
			scoreY = Math.pow(Math.E, (-(1/lm.getD()) * D));
		}
		else {
			Double D = distancesY1[0];
			scoreY = Math.pow(Math.E, (-(1/lm.getD()) * D));
		}
		
		// how near a corner on Z plane
		Double[] distancesZ0 = new Double[2];
		Double[] distancesZ1 = new Double[2];
		// distances to bottom Z
		distancesZ0[0] = Math.abs(obj.getMinZ() - lm.getMinZ());
		distancesZ0[1] = Math.abs(obj.getMaxZ() - lm.getMinZ());
		// distances to top Z
		distancesZ1[0] = Math.abs(obj.getMinZ() - lm.getMaxZ());
		distancesZ1[1] = Math.abs(obj.getMaxZ() - lm.getMaxZ());
		// get shortest distance to a corner
		Arrays.sort(distancesZ0);
		Arrays.sort(distancesZ1);
		if (distancesZ0[0] <= distancesZ1[0]) {
			Double D = distancesZ0[0];
			scoreZ = Math.pow(Math.E, (-(1/lm.getH()) * D));
		}
		else {
			Double D = distancesZ1[0];
			scoreZ = Math.pow(Math.E, (-(1/lm.getH()) * D));
		}
		
		return (scoreX + scoreY + scoreZ) / 3;
	}
}
