package Relations;

public class LocationOn extends Relation{
	
	@Override
	public double evaluate(Object obj, Object lm) {
//		double overlapW;
//		double overlapD;
//		double overlapA;
//		double objectA = obj.getW() * obj.getD();
//		double overlapRatio;
//		
//		// if object on top of landmark
//		if (obj.minZ() == lm.maxZ()) {
//			// width overlap
//			if (obj.minX() < lm.minX()) {
//				overlapW = obj.maxX() - lm.minX();
//			}
//			else {
//				overlapW = lm.maxX() - obj.minX();
//			}
//			// depth overlap
//			if (obj.minY() < lm.minY()) {
//				overlapD = obj.maxY() - lm.minY();
//			}
//			else {
//				overlapD = lm.maxY() - obj.minY();
//			}
//			// area overlap
//			overlapA = overlapW * overlapD;
//			overlapRatio = overlapA / objectA;
//			if (overlapRatio <= 0) {
//				return 0.0;
//			}
//			else {
//				return overlapRatio;
//			}
//		}
		return 0.0;
	}
}
