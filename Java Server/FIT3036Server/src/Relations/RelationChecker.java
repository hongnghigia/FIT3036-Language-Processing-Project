package Relations;

import java.util.ArrayList;

import UCG.ICGNode;
import UCG.Node;

public class RelationChecker {

	private ArrayList<Topological> relations = new ArrayList<Topological>();
	private ArrayList<Projective> projectives = new ArrayList<Projective>();
	private RelationDict relationsDict = new RelationDict();
	private LocationOn on = new LocationOn();
	private LocationNear near = new LocationNear();
	private LocationIn in = new LocationIn();
	private LocationAt at = new LocationAt();
	private LocationCenter center = new LocationCenter();
	private LocationEdge edge = new LocationEdge();
	private LocationCorner corner = new LocationCorner();
	private LocationEnd end = new LocationEnd();
	private ProjectiveFront front = new ProjectiveFront();
	
	public RelationChecker() {
		relations.add(on);
		relations.add(near);
		relations.add(in);
		relations.add(at);
		relations.add(center);
		relations.add(edge);
		relations.add(corner);
		relations.add(end);
		
		projectives.add(front);
	}
	
	public String getBestIcg(ArrayList<Node> icgs, ICGNode speaker) {
		ArrayList<Double> scores = new ArrayList<Double>();
		double score;
		for (Node o : icgs) {
			score = 0.0;
			if (o.hasChild()) {
				for (Node a : o.getChildren()) {
					score += getRelationScore((ICGNode) o, (ICGNode) a.getFirstChild(), a.getValue(), speaker);
					if (a.getFirstChild().hasChild()) {
						Node a2 = a.getFirstChild().getFirstChild();
						score += getRelationScore((ICGNode) a.getFirstChild(), (ICGNode) a2.getFirstChild(), a2.getValue(), speaker);
					}
				}
			}
			System.out.println("\nScore for " + o.getValue() + ":");
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
		System.out.println("\nBest Match: " + icgs.get(bestIndex).getValue());
		return icgs.get(bestIndex).getValue();
	}
	
	private double getRelationScore(ICGNode obj, ICGNode lm, String arc, ICGNode speaker) {
		if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_on") || 
				relationsDict.getSynonym(arc).equalsIgnoreCase("location_above")) {
			return relations.get(0).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_near")) {
			return relations.get(1).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_inside")) {
			return relations.get(2).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_at")){
			return relations.get(3).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_inthecenterof")){
			return relations.get(4).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_attheedgeof_on") || 
				relationsDict.getSynonym(arc).equalsIgnoreCase("location_attheedgeof_off")){
			return relations.get(5).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_inthecornerof_off") || 
				relationsDict.getSynonym(arc).equalsIgnoreCase("location_inthecornerof_on")){
			return relations.get(6).evaluate(obj, lm);
		}
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_attheendof_on") || 
				relationsDict.getSynonym(arc).equalsIgnoreCase("location_attheendof_off")){
			return relations.get(7).evaluate(obj, lm);
		} 
		else if (relationsDict.getSynonym(arc).equalsIgnoreCase("location_infrontof_off") ||
				relationsDict.getSynonym(arc).equalsIgnoreCase("location_infrontof_on")){
			return projectives.get(0).evaluate(obj, lm, speaker);
		} else {
			return 0.0;
		}
	}
}
