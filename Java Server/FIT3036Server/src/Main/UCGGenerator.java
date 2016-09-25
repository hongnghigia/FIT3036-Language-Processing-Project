package Main;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import UCG.Node;

public class UCGGenerator {
	private String obj;
	private ArrayList<String> lm;
	private ArrayList<String> pp;
	private ArrayList<Node> graphs = new ArrayList<Node>();
	private Node node;
	
	public UCGGenerator(String object, ArrayList<String> landmarks, ArrayList<String> prepositions) {
		obj = object;
		lm = landmarks;
		pp = prepositions;
	}
	
	public ArrayList<Node> createUCG() {
		int idCount = 1;
		
		// makes UCG of object
		if (!obj.equals("")) {
			node = new Node(idCount, "node", obj);
			graphs.add(node);
			idCount++;
			
			// dealing with one landmark
			if ((lm.size() > 0) && (lm.size() == pp.size())) {
				node = new Node(idCount, "arc", pp.get(0));
				graphs.get(0).addChild(node);
				idCount++;
				node = new Node(idCount, "node", lm.get(0));
				graphs.get(0).getLast().addChild(node);
				idCount++;
				
				// dealing with two landmarks
				if ((lm.size() > 1) && (lm.size() == pp.size())) {
					node = new Node(idCount, "arc", pp.get(1));
					graphs.get(0).getLast().addChild(node);
					idCount++;
					node = new Node(idCount, "node", lm.get(1));
					graphs.get(0).getLast().addChild(node);
					idCount++;
					
					// alternate UCG for two landmarks
					node = new Node(idCount, "node", obj);
					graphs.add(node);
					idCount++;
					for (int i = 0; i < lm.size(); i++) {
						node = new Node(idCount, "arc", pp.get(i));
						graphs.get(1).addChild(node);
						idCount++;
						node = new Node(idCount, "node", lm.get(i));
						graphs.get(1).getChild(pp.get(i)).addChild(node);
						idCount++;
					}
				}
			}
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(graphs));
		
		return graphs;
	}
}
