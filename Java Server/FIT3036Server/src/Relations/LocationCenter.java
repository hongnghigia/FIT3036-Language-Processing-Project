package Relations;

import UCG.ICGNode;

public class LocationCenter extends Relation {
	
	private double centerXL;
	private double centerYL;
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
	double evaluate(ICGNode obj, ICGNode lm) {
		// scale with landmark if its larger than 1m
		if (lm.getW() > 1) {
			sizeW = lm.getW();
		}
		else {
			sizeW = 1;
		}
		
		if (lm.getD() > 1) {
			sizeD = lm.getD();
		}
		else {
			sizeD = 1;
		}
		
		if (lm.getH() > 1) {
			sizeH = lm.getH();
		}
		else {
			sizeH = 1;
		}
		
		// center of landmark
		centerXL = lm.getMinX() + (lm.getW() * 0.5);
		centerYL = lm.getMinY() + (lm.getD() * 0.5);
		
		// touching center in X plane
		if (obj.getMinX() <= centerXL && obj.getMaxX() >= centerXL) {
			scoreX = 1;
		}
		// object is left of landmark's center
		else if (obj.getMaxX() < centerXL) {
			xD = centerXL - obj.getMaxX();
		}
		// object is right of landmark's center
		else if (obj.getMinX() > centerXL) {
			xD = obj.getMinX() - centerXL;
		}
		scoreX = Math.pow(Math.E, (-(1/sizeW) * xD));
		
		// touching center in Y plane
		if (obj.getMinY() <= centerYL && obj.getMaxY() >= centerYL) {
			scoreY = 1;
		}
		// object is back of landmark's center
		else if (obj.getMaxY() < centerYL) {
			yD = centerYL - obj.getMaxY();
		}
		// object is front of landmark's center
		else if (obj.getMinY() > centerYL) {
			yD = obj.getMinY() - centerYL;
		}
		scoreY = Math.pow(Math.E, (-(1/sizeD) * yD));
		
		// distance in Z plane
		zD = obj.getMinZ() - lm.getMaxZ();
		scoreZ = Math.pow(Math.E, (-(1/sizeH) * zD));
		
		// combine x y z scores
		return (scoreX + scoreY + scoreZ) / 3;
	}
}
