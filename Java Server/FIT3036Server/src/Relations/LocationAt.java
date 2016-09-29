package Relations;

import UCG.ICGNode;

public class LocationAt extends Relation {
	
	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		double boundary[] = {0.25, 0.25, 0.25};
		
		return 1.0;
	}

}
