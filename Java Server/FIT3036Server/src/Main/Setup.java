package Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * creates unlabeled arff file for classification
 */
public class Setup {
	
	private Checker c1 = new Checker();

	public void run(ArrayList<String> sentence) throws IOException {
		
		// deletes the file at the start of the program
		File f = new File("unlabelled.arff");
		f.delete();
		
		// writes attributes but not the data
		Writer w = new Writer();
		w.writeAttributes();
			
		// create String[], where [0] = word, [1] = next word, [2] = current index, [3] = total words
		for (int i = 0; i < sentence.size(); i++) {
			String[] toCheck = new String[4];
			toCheck[0] = sentence.get(i);
			if (i == (sentence.size() - 1)) {
				toCheck[1] = null;
			}
			else {
				toCheck[1] = sentence.get(i + 1);
			}
			toCheck[2] = Integer.toString(i);
			toCheck[3] = Integer.toString(sentence.size());
			
			// uses checker to check the word against each feature
			c1.check(toCheck);
		}
	}
}
