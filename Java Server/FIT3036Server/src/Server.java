import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Main.Setup;
import Main.XmlGen;
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
	String chosenString;
	ArrayList<String> sentence;
	ArrayList<String> tags;
	Classifier cls;
	Setup su = new Setup();
	String objectHead;
	ArrayList<String> landmarkHead;
	ArrayList<String> prep;
	int landmarkLimit = 2;
	
	public void runServer() {
		
		try {
			
			// runs the server and waits for client to connect
			server = new ServerSocket(1234);
			System.out.println("Server is now online.\nWaiting for connection on port 1234...");
			
			while (true) {
				// makes connection between client and server
				clientSocket = server.accept();
			
				// sets up input and output streams between client and server
				DIS = new DataInputStream(clientSocket.getInputStream());
				DOS = new DataOutputStream(clientSocket.getOutputStream());
				
				// handles printing of received messages
//				handleReceived(DIS.readUTF());
			}
		}
		
		catch (IOException io) {
			io.printStackTrace();
		} 
		catch (Exception e) {
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
	
	/**
	 * prints received messages
	 */
	public void handleReceived(String received) throws IOException {
		// if received feedback from client
		if (received.contains("Score:")) {
			System.out.println("Feedback " + received);
		}
		
		else {
			// if received multiple sentences (via speech)
			if (received.contains(", ")){
				// get the top result of all sentences
				ArrayList<String> allSentences = new ArrayList<String>();
				for (String s : received.split(", ")) {
					allSentences.add(s);
				}
				chosenString = allSentences.get(0);
				System.out.println("\nReceived: " + received);
				System.out.println("Chosen: " + chosenString);
			}
			
			// received sentence via text
			else {
				chosenString = received;
				System.out.println("\nReceived: " + chosenString);
			}
			
			// put words of chosenString into array list
			sentence = new ArrayList<String>();
			for (String w : chosenString.split("\\s+")) {
				sentence.add(w);
			}
			
			// create arff file and predicts classes for the words
			su.run(sentence);
			classify();
			createNodes();
		}	
	}
	
	/**
	 * predicts the classes for words in the chosen sentence
	 */
	private void classify() {
		try {
			// use .model file (the classifier)
			cls = (Classifier) weka.core.SerializationHelper.read("cls.model");

			// use new arff file of new sentence
			Instances unlabelled = new Instances(new BufferedReader(new FileReader("unlabelled.arff")));
			
			// set index of the CLASS attribute in the arff file (the last attribute)
			unlabelled.setClassIndex(unlabelled.numAttributes() - 1);
			
			// predict classes for the words in the chosen sentence
			tags = new ArrayList<String>();
			for (int i = 0; i < unlabelled.numInstances(); i++) {
				double clsLabel = cls.classifyInstance(unlabelled.instance(i));
				tags.add(unlabelled.classAttribute().value((int) clsLabel));
			}
			
			// clear the arff file
			PrintWriter pw = new PrintWriter("unlabelled.arff");
			pw.print("");
			pw.close();
			
			// test printing
			for (String t : tags) {
				System.out.println(t);
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createNodes() {
		try {
			landmarkHead = new ArrayList<String>();
			prep = new ArrayList<String>();
			
			// find object
			int count = 0;
			boolean found = false;
			while (count < tags.size()) {
				if (!found) {
					if (tags.get(count).contains("-O")) {
						found = true;
						if (count == (tags.size() - 1)) {
							objectHead = sentence.get(count);
						}
					}
				}
				else {
					if (!tags.get(count).equals("I-O")) {
						objectHead = sentence.get(count - 1);
						count = tags.size();
					}
				}
				count++;
			}
			
			// find landmark(s)
			count = 0;
			found = false;
			int landmarkCount = 0;
			while ((count < tags.size()) && (landmarkCount < landmarkLimit)) {
				if (!found) {
					if (tags.get(count).contains("-L")) {
						found = true;
						if (count == (tags.size() - 1)) {
							landmarkHead.add(sentence.get(count));
							landmarkCount++;
							found = false;
						}
					}
				}
				else {
					if ((tags.get(count).equals("I-L")) && (count == (tags.size() - 1))) {
						landmarkHead.add(sentence.get(count));
						landmarkCount++;
						found = false;
					}
					else if (!tags.get(count).equals("I-L")) {
						landmarkHead.add(sentence.get(count - 1));
						landmarkCount++;
						found = false;
					}
				}
				count++;
			}
			
			// find spatial relations
			count = 0;
			found = false;
			landmarkCount = 0;
			String SP = "";
			while ((count < tags.size()) && (landmarkCount < landmarkLimit)) {
				if (!found) {
					if ((tags.get(count).contains("-P")) || (tags.get(count).contains("-S"))) {
						SP = SP + sentence.get(count) + " ";
						found = true;
					}
				}
				else {
					if ((tags.get(count).contains("-P")) || (tags.get(count).contains("-S"))) {
						SP = SP + sentence.get(count) + " ";
					}
					else if (tags.get(count).contains("-L")) {
						SP = SP.substring(0, SP.length() - 1);
						prep.add(SP);
						SP = "";
						landmarkCount++;
						found = false;
					}
				}
				count++;
			}
			
			System.out.println("\n" + objectHead + "\n");
			for (String s : landmarkHead) {
				System.out.println(s);
			}
			System.out.println();
			for (String s : prep) {
				System.out.println(s);
			}
			
			XmlGen gen = new XmlGen(objectHead, prep, landmarkHead);
			gen.run();
		}
		catch (Exception e){
			System.out.println("Failed to create nodes...");
		}
	}
}
