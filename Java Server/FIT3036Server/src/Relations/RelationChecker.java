package Relations;

import java.util.ArrayList;

import UCG.ICGNode;
import UCG.Node;

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
	
	public void getBestIcg(ArrayList<Node> icgs) {
		ArrayList<Double> scores = new ArrayList<Double>();
		double score;
		for (Node o : icgs) {
			score = 0.0;
			if (o.hasChild()) {
				for (Node a : o.getChildren()) {
					score += getRelationScore((ICGNode) o, (ICGNode) a.getFirstChild(), a.getValue());
					if (a.getFirstChild().hasChild()) {
						Node a2 = a.getFirstChild().getFirstChild();
						score += getRelationScore((ICGNode) a.getFirstChild(), (ICGNode) a2.getFirstChild(), a2.getValue());
					}
				}
			}
			System.out.println(score);
			scores.add(score);
		}
		
		double bestScore = 0;
		int bestIndex = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) > bestScore) {
				bestScore = scores.get(i);
				bestIndex = i;
			}
		}
		System.out.println(icgs.get(bestIndex).getValue());
	}
	
	private double getRelationScore(ICGNode obj, ICGNode lm, String arc) {
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
