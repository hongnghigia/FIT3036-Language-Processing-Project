import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import UCG.*;

public class Test {
	
	public void runTest(){
		Node node1 = new Node("1", "object", "mug");
		Node node2= new Node("2", "relation", "on");
		
		node1.addChild(node2);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		System.out.println(gson.toJson(node1));
	}
}
