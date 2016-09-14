import java.io.IOException;
import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) throws IOException {
		
		Server server = new Server();
		
		// TESTING CODE: 
		// uncomment to test XML file generation using sentence from user input
		System.out.println("Enter a sentence (e.g. the mug on the table)...");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		sc.close();
		server.handleReceived(s);
		
//		server.runServer();
	}
}
