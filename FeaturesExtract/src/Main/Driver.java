package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	private static String[] sc;
	public static void main(String[] args) throws IOException{
		// read textfile word by word and put it in the checker.
		Checker c1 = new Checker();
		String[] tags = {":O", ":P", ":L", ":S", ":A", ":N", ": O", ": P", ": L", ": S", ": A", ": N"};
		
		try {
			
			// removing | and :
			String content = new Scanner(new File("All_tagged.txt")).useDelimiter("\\Z").next();
			content = content.replace("|", "");
			for (String s : tags) {
				content = content.replace(s, "");
			}

			sc = content.split("\\s+");
			
			// deletes the file at the start of the program
			File f = new File("outputs.arff");
			f.delete();
			
			// writes attributes except data
			Writer w = new Writer();
			w.writeAttributes();
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		// for each word in the list, loop through it
		for(int i=0; i < sc.length; i++){
			String word = sc[i];
			c1.check(word);
		}
	}
}
