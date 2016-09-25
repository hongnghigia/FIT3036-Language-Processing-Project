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
	public Icg(ArrayList<Node> graphs){
		this.ucg = graphs;
	}
	
	public void createICG(){
		KbProcessor kbreader = new KbProcessor("image1.kb");
		int id=0;
		for (Node node : ucg){
			ArrayList<String> names = kbreader.getProperties(node.getValue());
			ArrayList<Node> children = node.getChildren();
			for (String name : names){
				Node icgNode = new Node(id, node.getRole(), name);
				graph.add(icgNode);
				id += 1;
			}
			
			for (Node child : children){
				if(child.getRole().equals("arc")){
					Node icgnode = new Node(id, child.getRole(), child.getValue());
					
				}
			}
		}
		
		System.out.println("Hello");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(graph));	
	}
}
