package Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * prepares the input for feature extraction
 */
public class Setup {

	public void run() throws IOException {
		Checker c1 = new Checker();
		
		// deletes the file at the start of the program
		File f = new File("unlabelled.arff");
		f.delete();
		
		// writes attributes but not the data
		Writer w = new Writer();
		w.writeAttributes();
		
		String test = "the mug on the table";
		
		// need array list of words for current line so that we can access specific indexes
		ArrayList<String> line = new ArrayList<String>();
		for (String u : test.split("\\s+")) {
			line.add(u);
		}
			
		// create String[], where [0] = word, [1] = next word, [2] = current index, [3] = total words
		for (int i = 0; i < line.size(); i++) {
			String[] toCheck = new String[4];
			toCheck[0] = line.get(i);
			if (i == (line.size() - 1)) {
				toCheck[1] = null;
			}
			else {
				toCheck[1] = line.get(i + 1);
			}
			toCheck[2] = Integer.toString(i);
			toCheck[3] = Integer.toString(line.size());
			
			// uses checker to check the word against each feature
			c1.check(toCheck);
		}
	}
}
