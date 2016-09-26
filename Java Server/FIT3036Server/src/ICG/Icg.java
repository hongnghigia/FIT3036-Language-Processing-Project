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
	private ArrayList<Node> graph = new ArrayList<Node>();
	private ArrayList<Node> objects = new ArrayList<Node>();
	private ArrayList<Node> relations = new ArrayList<Node>();
	private ArrayList<Node> landmarks = new ArrayList<Node>();
	private KbProcessor kbreader = new KbProcessor("image1.kb");
	
	public Icg(ArrayList<Node> graphs){
		this.ucg = graphs;
	}
	
	public void createICG(){
		preparation();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(graph));	
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
		
		System.out.println(node.getValue());
		while(node.hasChild() == true){
			Node nxtNode = node.next();
			System.out.println(nxtNode.getValue());
			if(nxtNode.getRole().equals("arc")){
				relations.add(nxtNode);
			}
			if(nxtNode.getRole().equals("node")){
				ArrayList<String> lmNames = kbreader.getProperties(nxtNode.getValue());
				for (String name : lmNames){
					Node aNode = new Node(id, "node", name);
					System.out.println(name);
					landmarks.add(aNode);
					id += 1;
				}	
			}
			
			node = nxtNode;
			}
		
//		for (int i =0; i < landmarks.size(); i++){
//			System.out.println(landmarks.get(i).getValue());
//		}
		
		}
	
	public void straightGraph(){
		
	}
	
}
