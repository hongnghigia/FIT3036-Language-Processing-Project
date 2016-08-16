package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	private static Scanner sc = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Checker c1 = new Checker();
		String[] tags = {":O", ":P", ":L", ":S", ":A", ":N", ": O", ": P", ": L", ": S", ": A", ": N"};
		
		try {
			//sc = new Scanner(new File("GIA NGHI.txt"));
			String content = new Scanner(new File("All_tagged.txt")).useDelimiter("\\Z").next();
			content = content.replace("|", "");
			for (String s : tags) {
				content = content.replace(s, "");
			}
			System.out.println(content);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} 
		
//		while(sc.hasNext()){
//			String word = sc.next();
//			c1.check(word);
//		}
	}
}
