package Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Features.IS_PP;

/**
 * creates unlabeled arff file for classification
 */
public class Setup {
	
	private Checker c1 = new Checker();
	private ArrayList<String> pplist; 
	public void run(ArrayList<String> sentence) throws IOException {
		
		// deletes the file at the start of the program
		File f = new File("unlabelled.arff");
		f.delete();
		
		IS_PP pp = new IS_PP();
		pplist = pp.getPP();
		
		// writes attributes but not the data
		Writer w = new Writer();
		w.writeAttributes();
			
		// create String[], where [0] = word, [1] = next word, [2] = current index, [3] = total words
		int counter = 0;
		int ppcounter = 0;
		for (int i = 0; i < sentence.size(); i++) {
			String[] toCheck = new String[6];
			toCheck[0] = sentence.get(i);
			if (i == (sentence.size() - 1)) {
				toCheck[1] = null;
			}
			else {
				toCheck[1] = sentence.get(i + 1);
			}
			toCheck[2] = Integer.toString(i);
			toCheck[3] = Integer.toString(sentence.size());
			
			if (sentence.get(i).toLowerCase().equals("the") && counter == 0){
				toCheck[5] = "0,";
				counter += 1;
			} else {
				toCheck[5] = "1,";
			}
			toCheck[4] = Integer.toString(ppcounter);
			if (pplist.contains(sentence.get(i)) && ppcounter == 0){
				toCheck[4] = "0,";
				ppcounter += 1;
			}
			else if (ppcounter == 0){
				toCheck[4] = "0,";
			} else if (ppcounter > 0){
				toCheck[4] = "1,";
			}
			
			// uses checker to check the word against each feature
			c1.check(toCheck);
		}
	}
}
