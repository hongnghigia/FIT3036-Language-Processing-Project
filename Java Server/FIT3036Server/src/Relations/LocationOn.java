package Relations;

import UCG.ICGNode;

public class LocationOn extends Relation {
	
	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		double overlapW;
		double overlapD;
		double overlapA;
		double objectA = obj.getW() * obj.getD();
		double overlapRatio;
		
		// if object on top of landmark
		if (obj.getMinZ() == lm.getMaxZ()) {
			// width overlap
			if (obj.getMinX() < lm.getMinX()) {
				if (obj.getMaxX() <= lm.getMinX()) {
					overlapW = 0.0;
				}
				else {
					overlapW = obj.getMaxX() - lm.getMinX();
				}
			}
			else {
				if (obj.getMaxX() <= lm.getMaxX()) {
					overlapW = obj.getW();
				}
				else if (obj.getMinX() >= lm.getMaxX()) {
					overlapW = 0.0;
				}
				else {
					overlapW = lm.getMaxX() - obj.getMinX();
				}
			}
			// depth overlap
			if (obj.getMinY() < lm.getMinY()) {
				if (obj.getMaxY() <= lm.getMinY()) {
					overlapD = 0.0;
				}
				else {
					overlapD = obj.getMaxY() - lm.getMinY();
				}
			}
			else {
				if (obj.getMaxY() <= lm.getMaxY()) {
					overlapD = obj.getD();
				}
				else if (obj.getMinY() >= lm.getMaxY()) {
					overlapD = 0.0;
				}
				else {
					overlapD = lm.getMaxY() - obj.getMinY();
				}
			}
			// area overlap
			overlapA = overlapW * overlapD;
			overlapRatio = overlapA / objectA;
			if (overlapRatio <= 0) {
				return 0.0;
			}
			else {
				return overlapRatio;
			}
		}
		return 0.0;
	}
}
