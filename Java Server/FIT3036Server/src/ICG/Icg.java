package ICG;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import UCG.*;

public class Icg {
	private ArrayList<Node> ucg = new ArrayList<Node>();
	private ArrayList<Node> icgs = new ArrayList<Node>();
	private ArrayList<Node> objects = new ArrayList<Node>();
	private ArrayList<Node> relations = new ArrayList<Node>();
	private ArrayList<Node> landmarks1 = new ArrayList<Node>();
	private ArrayList<Node> landmarks2 = new ArrayList<Node>();
	private ArrayList<Node> tmpLM = new ArrayList<Node>();
	private KbProcessor kbreader = new KbProcessor("image1.kb");
	
	public Icg(ArrayList<Node> graphs){
		this.ucg = graphs;
	}
	
	public void createICG(){
		preparation();
		straightGraph();
		preparation();
		if (relations.size() > 1){
			branchingGraph();
			}
		
		//System.out.println(icgs);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(gson.toJson(icgs));
	}
	
	public void preparation(){
		objects.clear();
		tmpLM.clear();
		landmarks1.clear();
		landmarks2.clear();
		relations.clear();
		
		int id=0;
		Node node = ucg.get(0);
		ArrayList<String> names = kbreader.getNames(ucg.get(0).getValue());
		for(String name : names){
			HashMap<String, String> properties = kbreader.getProperties(name);
			ICGNode icgNode = new ICGNode(id, node.getRole(), name, properties);
			objects.add(icgNode);
			id += 1;
		}
		
		while(node.hasChild() == true){
			Node nxtNode = node.next();
//			System.out.println(nxtNode.getValue());

			if(nxtNode.getRole().equals("arc")){
				Node aNode =new Node(id, "arc", nxtNode.getValue());
				relations.add(aNode);
				id += 1;
			}
			if(nxtNode.getRole().equals("node")){
//				System.out.println(nxtNode.getValue() + "\n");
				
				ArrayList<String> lmNames = kbreader.getNames(nxtNode.getValue());
				for (String name : lmNames){
					HashMap<String, String> lmProperties = kbreader.getProperties(name);
					//System.out.println(lmProperties);
					ICGNode aNode = new ICGNode(id, "node", name, lmProperties);
					//aNode.showProperties();
					tmpLM.add(aNode);
					id += 1;
				}
				lmNames.clear();
			}
			node = nxtNode;
			}
		// putting each landmarks to its respected list, i.e table1, table2 go to "table" list.
		String str = tmpLM.get(0).getValue();
		String testStr = str.substring(0, str.length()-1);
		for (Node i : tmpLM){
			String tmp = i.getValue();
			if (tmp.toLowerCase().contains(testStr)){
				landmarks1.add(i);
			} else {
				landmarks2.add(i);
			}
		}
	}
	
	public void straightGraph(){
		if(relations.size() > 1){
			
			for (Node o : objects){
				for (Node l : landmarks1){
					for (Node lm : landmarks2){
						//flushing children
						relations.get(1).clearChildren();
						relations.get(0).clearChildren();
						l.clearChildren();
						o.clearChildren();
						
						//adding to the icgs
						relations.get(1).addChild(lm);
						l.addChild(relations.get(1));
						relations.get(0).addChild(l);
						o.addChild(relations.get(0));
						icgs.add(o);
					}
				}
			}
		}
		
		else {
			if (relations.size() == 1){
				for (Node o : objects){
					for (Node l : landmarks1){
						//flushing children
						relations.get(0).clearChildren();
						l.clearChildren();
						o.clearChildren();
						
						relations.get(0).addChild(l);
						o.addChild(relations.get(0));
						icgs.add(o);
					}
				}
			}
		}
	}
	
	public void branchingGraph(){
		for (Node o : objects){
			for (Node l : landmarks1){
				for (Node lm : landmarks2){
					//flushing children
					relations.get(1).clearChildren();
					relations.get(0).clearChildren();
					l.clearChildren();
					o.clearChildren();
					
					relations.get(0).addChild(l);
					relations.get(1).addChild(lm);
					o.addChild(relations.get(0));
					o.addChild(relations.get(1));
					
					icgs.add(o);
				}
			}
		}
	}
	
	public ArrayList<Node> getIcgs() {
		return icgs;
	}
}
