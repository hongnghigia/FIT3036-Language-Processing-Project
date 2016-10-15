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
	private KbProcessor kbreader;
	
	public Icg(ArrayList<Node> graphs, String imageKB){
		this.ucg = graphs;
		kbreader = new KbProcessor(imageKB);
	}
	
	public ArrayList<Node> createICG(){
		preparation();
		straightGraph();
		if (relations.size() > 1){
			branchingGraph();
			}
		
		//System.out.println(icgs);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(icgs));
		return icgs;
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
		if (tmpLM.size() != 0) {
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
	}
	
	public void straightGraph(){
		if(relations.size() > 1){
			for (Node o : objects){
				for (Node l : landmarks1){
					for (Node l2 : landmarks2){
						Node a = relations.get(0);
						Node a2 = relations.get(1);
						ICGNode obj = new ICGNode(o.getId(), o.getRole(), o.getValue(), ((ICGNode) o).getProperties());
						Node r = new Node(a.getId(), a.getRole(), a.getValue());
						Node r2 = new Node(a2.getId(), a2.getRole(), a2.getValue()); 
						ICGNode lm = new ICGNode(l.getId(), l.getRole(), l.getValue(), ((ICGNode) l).getProperties());
						ICGNode lm2 = new ICGNode(l2.getId(), l2.getRole(), l2.getValue(), ((ICGNode) l2).getProperties());
						
						r2.addChild(lm2);
						lm.addChild(r2);
						r.addChild(lm);
						obj.addChild(r);
						icgs.add(obj);
					}
				}
			}
		}
		
		else if (relations.size() == 1){
			for (Node o : objects){
				for (Node l : landmarks1){
					Node a = relations.get(0);
					ICGNode obj = new ICGNode(o.getId(), o.getRole(), o.getValue(), ((ICGNode) o).getProperties());
					Node r = new Node(a.getId(), a.getRole(), a.getValue());
					ICGNode lm = new ICGNode(l.getId(), l.getRole(), l.getValue(), ((ICGNode) l).getProperties());
					
					r.addChild(lm);
					obj.addChild(r);
					icgs.add(obj);
				}
			} 
		}
		else if (relations.size() < 1) {
			Node o = objects.get(0);
			ICGNode obj = new ICGNode(o.getId(), o.getRole(), o.getValue(), ((ICGNode) o).getProperties());
			icgs.add(obj);
		}
	}
	
	public void branchingGraph(){
		for (Node o : objects){
			for (Node l : landmarks1){
				for (Node l2 : landmarks2){
					Node a = relations.get(0);
					Node a2 = relations.get(1);
					ICGNode obj = new ICGNode(o.getId(), o.getRole(), o.getValue(), ((ICGNode) o).getProperties());
					Node r = new Node(a.getId(), a.getRole(), a.getValue());
					Node r2 = new Node(a2.getId(), a2.getRole(), a2.getValue()); 
					ICGNode lm = new ICGNode(l.getId(), l.getRole(), l.getValue(), ((ICGNode) l).getProperties());
					ICGNode lm2 = new ICGNode(l2.getId(), l2.getRole(), l2.getValue(), ((ICGNode) l2).getProperties());
					
					r.addChild(lm);
					r2.addChild(lm2);
					obj.addChild(r);
					obj.addChild(r2);
					icgs.add(obj);
				}
			}
		}
	}
	
	public ArrayList<Node> getIcgs() {
		return icgs;
	}
	
	public ICGNode getSpeaker(){
		HashMap<String, String> properties = kbreader.getProperties("speaker");
		ICGNode speaker = new ICGNode(0, "node", "speaker", properties);
		return speaker;
	}
}
