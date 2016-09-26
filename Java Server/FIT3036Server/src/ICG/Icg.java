package ICG;
import java.util.ArrayList;

import org.drools.*;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import UCG.*;

public class Icg {
	private ArrayList<Node> ucg = new ArrayList<Node>();
	private ArrayList<Node> icgs = new ArrayList<Node>();
	private ArrayList<Node> graph = new ArrayList<Node>();
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
	}
	
	public void preparation(){
		int id=0;
		Node node = ucg.get(0);
		ArrayList<String> names = kbreader.getProperties(ucg.get(0).getValue());
		for(String name : names){
			Node icgNode = new Node(id, node.getRole(), name);
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
				
				ArrayList<String> lmNames = kbreader.getProperties(nxtNode.getValue());
				for (String name : lmNames){
					Node aNode = new Node(id, "node", name);
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
				System.out.println(i.getValue());
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
						relations.get(1).addChild(lm);
						l.addChild(relations.get(1));
						relations.get(0).addChild(l);
						o.addChild(relations.get(0));
						icgs.add(o);
					}
				}
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(icgs));	
	}
	
}
