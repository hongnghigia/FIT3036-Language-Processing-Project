package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
	private static Scanner sc = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Checker c1 = new Checker();
		try {
			sc = new Scanner(new File("GIA NGHI.txt"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		while(sc.hasNext()){
			String word = sc.next();
			c1.check(word);
		}
	}
}
