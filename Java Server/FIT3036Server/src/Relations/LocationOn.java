package Relations;

import UCG.ICGNode;

public class LocationOn extends Topological {
	
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
			// if object <= left edge of landmark
			if (obj.getMinX() < lm.getMinX()) {
				// if object is completely off landmark
				if (obj.getMaxX() <= lm.getMinX()) {
					overlapW = 0.0;
				}
				// calculate overlap on left edge
				else {
					overlapW = obj.getMaxX() - lm.getMinX();
				}
			}
			
			// if object > left edge of landmark
			else {
				// if object is completely on landmark
				if (obj.getMaxX() <= lm.getMaxX()) {
					overlapW = obj.getW();
				}
				// if object is completely off landmark
				else if (obj.getMinX() >= lm.getMaxX()) {
					overlapW = 0.0;
				}
				// calculate overlap on right edge
				else {
					overlapW = lm.getMaxX() - obj.getMinX();
				}
			}
			
			// depth overlap
			// if object <= back edge of landmark
			if (obj.getMinY() < lm.getMinY()) {
				// if object is completely off landmark
				if (obj.getMaxY() <= lm.getMinY()) {
					overlapD = 0.0;
				}
				// calculate overlap on back edge
				else {
					overlapD = obj.getMaxY() - lm.getMinY();
				}
			}
			
			// if object > back edge of landmark
			else {
				// if object is completely on landmark
				if (obj.getMaxY() <= lm.getMaxY()) {
					overlapD = obj.getD();
				}
				// if object is completely off landmark
				else if (obj.getMinY() >= lm.getMaxY()) {
					overlapD = 0.0;
				}
				// calculate overlap on front edge
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
		return 0.001;
	}
}
