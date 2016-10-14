package Relations;

import UCG.ICGNode;

public class LocationCenter extends Relation {
	
	private double centerXL;
	private double centerYL;
	private double scoreX;
	private double scoreY;
	private double scoreZ;

	@Override
	double evaluate(ICGNode obj, ICGNode lm) {
		// center of landmark
		centerXL = lm.getMinX() + (lm.getW() * 0.5);
		centerYL = lm.getMinY() + (lm.getD() * 0.5);
		
		// touching center in X plane
		if (obj.getMinX() <= centerXL && obj.getMaxX() >= centerXL) {
			scoreX = 1;
		}
		// object is left of landmark's center
		else if (obj.getMaxX() < centerXL) {
			double D = centerXL - obj.getMaxX();
			scoreX = Math.pow(Math.E, (-1 * D));
		}
		// object is right of landmark's center
		else if (obj.getMinX() > centerXL) {
			double D = obj.getMinX() - centerXL;
			scoreX = Math.pow(Math.E, (-1 * D));
		}
		
		// touching center in Y plane
		if (obj.getMinY() <= centerYL && obj.getMaxY() >= centerYL) {
			scoreY = 1;
		}
		// object is back of landmark's center
		else if (obj.getMaxY() < centerYL) {
			double D = centerYL - obj.getMaxY();
			scoreY = Math.pow(Math.E, (-1 * D));
		}
		// object is front of landmark's center
		else if (obj.getMinY() > centerYL) {
			double D = obj.getMinY() - centerYL;
			scoreY = Math.pow(Math.E, (-1 * D));
		}
		
		// distance in Z plane
		double D = obj.getMinZ() - lm.getMaxZ();
		scoreZ = Math.pow(Math.E, (-1 * D));
		
		return (scoreX + scoreY + scoreZ) / 3;
	}
}
