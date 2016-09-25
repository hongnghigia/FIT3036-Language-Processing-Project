package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instances;

import ICG.*;

public class InputHandler {
	
	private Setup su;
	private Classifier cls;
	private String chosenString;
	private ArrayList<String> sentence;
	private ArrayList<String> tags;
	private String objectHead;
	private ArrayList<String> landmarkHead;
	private ArrayList<String> prep;
	private int landmarkLimit = 2;
	private UCGGenerator ucgg;
	private Icg icg;
	
	public InputHandler(Setup su) {
		this.su = su;
	}

	/**
	 * prints received messages
	 */
	public void handleInput(String received) throws IOException {
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
			
			// generates possible UCGs for the sentence
			ucgg = new UCGGenerator(objectHead, landmarkHead, prep);
			icg = new Icg(ucgg.createUCG());
			icg.createICG();
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
			
			for (String t : tags) {
				System.out.println(t);
			}
			
			// clear the arff file
			PrintWriter pw = new PrintWriter("unlabelled.arff");
			pw.print("");
			pw.close();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * separates the head nouns and prepositions
	 */
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
			
		}
		catch (Exception e){
			System.out.println("Failed to create nodes...");
		}
	}
}
