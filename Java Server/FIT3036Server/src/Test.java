import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import UCG.*;

public class Test {
	Node node;
	ArrayList<Node> graphs = new ArrayList<Node>();
	
	public void runTest(){
	
		node = new Node(1, "object", "mug");
		graphs.add(node);
		node = new Node(2, "relation", "on");
		graphs.get(0).addChild(node);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		System.out.println(gson.toJson(graphs));
	}
}
