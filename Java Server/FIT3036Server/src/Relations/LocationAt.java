package Relations;

import java.util.Arrays;

import UCG.ICGNode;

public class LocationAt extends Relation {
	LocationOn lo = new LocationOn();
	LocationNear ln = new LocationNear();
	LocationIn li = new LocationIn();
	
	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		double scoreOn = lo.evaluate(obj, lm);
		double scoreNear = ln.evaluate(obj, lm);
		double scoreIn = li.evaluate(obj, lm);
		
		Double[] scores = {scoreOn, scoreNear, scoreIn};
		Arrays.sort(scores);
		return scores[2];
	}

}
