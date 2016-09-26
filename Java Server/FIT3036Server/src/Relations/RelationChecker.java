package Relations;

import java.util.ArrayList;

public class RelationChecker {

	private ArrayList<Relation> relations = new ArrayList<Relation>();
	private LocationOn on = new LocationOn();
	private LocationNear near = new LocationNear();
	private LocationIn in = new LocationIn();
	private LocationAt at = new LocationAt();
	
	public RelationChecker() {
		relations.add(on);
		relations.add(near);
		relations.add(in);
		relations.add(at);
	}
	
	public double getRelationScore(Object obj, Object lm, String arc) {
		if (arc.equalsIgnoreCase("on")) {
			return relations.get(0).evaluate(obj, lm);
		}
		else if (arc.equalsIgnoreCase("near")) {
			return relations.get(1).evaluate(obj, lm);
		}
		else if (arc.equalsIgnoreCase("in")) {
			return relations.get(2).evaluate(obj, lm);
		}
		else if (arc.equalsIgnoreCase("at")){
			return relations.get(3).evaluate(obj, lm);
		}
		else {
			return 0.0;
		}
	}
}
