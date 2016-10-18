package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Features.IS_PP;

/**
 * prepares the input for feature extraction
 */
public class Setup {
	ArrayList<String> pplist;
	public void run() {
		IS_PP pp = new IS_PP();
		pplist = pp.getPP();
		Checker c1 = new Checker();
		ArrayList<String> classes; 	// stores the class tags (B-O, I-O, B-L, etc.)
		int consClasses;			// how many consecutive classes of the same class in a row
		String tmp;					// each line from the input, formatted so that it is just the words
		boolean beginning;			// used to determine if beginning or intermediate of a class tag (B-O or I-O)
		
		try {
			// deletes the file at the start of the program
			File f = new File("outputs.arff");
			f.delete();
			
			// writes attributes but not the data
			Writer w = new Writer();
			w.writeAttributes();
			
			// read in from the input text file
			BufferedReader in = new BufferedReader(new FileReader("All_tagged.txt"));
			String str;
			
			// remove all "|" and unnecessary spaces that follow a ":"
			List<String> content = new ArrayList<String>();
			while ((str = in.readLine()) != null) {
				str = str.replace("|", "");
				str = str.replace(": ", ":");
				str = str.replace(" '", "");
				str = str.replace("'", "");
				content.add(str);
			}
			
			// check the features of one line of the input file at a time
			for (String s : content) {
				classes = new ArrayList<String>();
				consClasses = 0;
				tmp = s; 
				beginning = true;
				
				// keep a counter of how many consecutive words are classified in the same class
				for (String t : s.split("\\s+")) {
					consClasses += 1;
					if (t.contains(":")) {
						String[] parts = t.split(":");
						
						if (parts[1].toLowerCase().equals("o")) {
							for (int k = 0; k < consClasses; k++) {
								if (beginning) {
									classes.add("B-O");
									beginning = false;
								}
								else {
									classes.add("I-O");
								}
							}
						}
						
						else if (parts[1].toLowerCase().equals("p")) {
							for (int k = 0; k < consClasses; k++) {
								if (beginning) {
									classes.add("B-P");
									beginning = false;
								}
								else {
									classes.add("I-P");
								}
							}
						}
						
						else if (parts[1].toLowerCase().equals("l")) {
							for (int k = 0; k < consClasses; k++) {
								if (beginning) {
									classes.add("B-L");
									beginning = false;
								}
								else {
									classes.add("I-L");
								}
							}
						}
						
						else if (parts[1].toLowerCase().equals("s")) {
							for (int k = 0; k < consClasses; k++) {
								if (beginning) {
									classes.add("B-S");
									beginning = false;
								}
								else {
									classes.add("I-S");
								}
							}
						}
						
						else if (parts[1].toLowerCase().equals("a")) {
							for (int k = 0; k < consClasses; k++) {
								if (beginning) {
									classes.add("B-A");
									beginning = false;
								}
								else {
									classes.add("I-A");
								}
							}
						}
						
						else if (parts[1].toLowerCase().equals("n")) {
							for (int k = 0; k < consClasses; k++) {
								if (beginning) {
									classes.add("B-N");
									beginning = false;
								}
								else {
									classes.add("I-N");
								}
							}
						}
						
						consClasses = 0;
						tmp = tmp.replace(t, parts[0]);
						beginning = true;
					}
				}
				
				// need array list of words for current line so that we can access specific indexes
				ArrayList<String> line = new ArrayList<String>();
				for (String u : tmp.split("\\s+")) {
					line.add(u);
				}
				
				// create String[], where [0] = word, [1] = next word, [2] = current index, [3] = total words, [4] = class
				// [5] - first the
				// [6] - first pp
				int counter = 0;
				int ppcounter = 0;
				for (int i = 0; i < line.size(); i++) {
					String[] toCheck = new String[7];
					toCheck[0] = line.get(i);
					if (i == (line.size() - 1)) {
						toCheck[1] = null;
					}
					else {
						toCheck[1] = line.get(i + 1);
					}
					toCheck[2] = Integer.toString(i);
					toCheck[3] = Integer.toString(line.size());
					toCheck[4] = classes.get(i);
					if (line.get(i).toLowerCase().equals("the") && counter == 0){
						toCheck[5] = "0,";
						counter += 1;
					} else {
						toCheck[5] = "1,";
					}
					
					if (pplist.contains(line.get(i)) && ppcounter == 0){
						toCheck[6] = "0,";
						ppcounter += 1;
					} else {
						toCheck[6] = "1,";
					}
					
					// uses checker to check the word against each feature
					c1.check(toCheck);
				}
			}
			in.close();
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
