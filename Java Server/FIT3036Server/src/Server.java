import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Main.Setup;
import UCG.Node;
import weka.classifiers.Classifier;
import weka.core.*;
import java.io.BufferedReader;
import java.io.FileReader;

// imports for xml creator
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Server {

	ServerSocket server;
	Socket clientSocket;
	DataInputStream DIS;
	DataOutputStream DOS;
	String receivedFromClient;
	String fixedString;
	Classifier cls;
	
	public void runServer() {
		
		Setup su = new Setup();
		
		try {
			
			su.run();
			cls = (Classifier) weka.core.SerializationHelper.read("cls.model");
			
			Instances unlabelled = new Instances(new BufferedReader(new FileReader("unlabelled.arff")));
			
			unlabelled.setClassIndex(unlabelled.numAttributes() - 1);
			
			for (int i = 0; i < unlabelled.numInstances(); i++) {
				double clsLabel = cls.classifyInstance(unlabelled.instance(i));
				System.out.println(clsLabel + " -> " + unlabelled.classAttribute().value((int) clsLabel));
			}
			
			Node node = new Node();
			node.setLabel("node1");
			node.setKind("base");
			node.setKey("called");
			node.setKey("mug");
			
			try{
				File file = new File("ucg.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						
				// output
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				jaxbMarshaller.marshal(node, file);
				jaxbMarshaller.marshal(node, System.out);
			} catch (JAXBException e){
				e.printStackTrace();
			}
			
			
			// runs the server and waits for client to connect
			server = new ServerSocket(1234);
			System.out.println("Server is now online.\nWaiting for connection on port 1234...");
			
			while (true) {
				// makes connection between client and server
				clientSocket = server.accept();
			
				// sets up input and output streams between client and server
				DIS = new DataInputStream(clientSocket.getInputStream());
				DOS = new DataOutputStream(clientSocket.getOutputStream());
				
				// prints what the client sent to the server, and sends to client
				receivedFromClient = DIS.readUTF();
				if (receivedFromClient.contains("Score:")) {
					System.out.println("Feedback " + receivedFromClient);
				}
				else {
					fixedString = receivedFromClient.substring(1, receivedFromClient.length()-1);
					System.out.println("\n" + '"' + receivedFromClient + '"');
				}		
			}
		}
		
		catch (IOException io) {
			io.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		finally {
			// terminated connection to client
			if (clientSocket != null) {
				try {
					clientSocket.close();
				}
				catch (IOException io) {
					io.printStackTrace();
				}
			}
			
			//shutdown server
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException io) {
					io.printStackTrace();
				}
			}
			
			System.out.println("Server is now offline.\n");
		}
	}
}
