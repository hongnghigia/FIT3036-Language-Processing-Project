package Relations;

import UCG.ICGNode;

public class LocationFront extends Topological {
	
	private double centerXO;
	private double centerYO;
	private double centerXL;
	private double centerYL;

	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		// find center of object and landmark
		centerXO = lm.getMinX() + (obj.getW() * 0.5);
		centerYO = lm.getMinY() + (obj.getD() * 0.5);
		centerXL = lm.getMinX() + (lm.getW() * 0.5);
		centerYL = lm.getMinY() + (lm.getD() * 0.5);
		
		// 
		
		return 0.0;
	}
}
