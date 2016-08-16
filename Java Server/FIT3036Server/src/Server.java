import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket server;
	Socket clientSocket;
	DataInputStream DIS;
	DataOutputStream DOS;
	String receivedFromClient;
	String fixedString;
	
	public void runServer() {
		try {
			// runs the server and waits for client to connect
			server = new ServerSocket(1234);
			System.out.println("Server is now online.\nWaiting for connection on port 1234...\n");
			
			while (true) {
				// makes connection between client and server
				clientSocket = server.accept();
			
				// sets up input and output streams between client and server
				DIS = new DataInputStream(clientSocket.getInputStream());
				DOS = new DataOutputStream(clientSocket.getOutputStream());
				
				// prints what the client sent to the server, and sends to client
				receivedFromClient = DIS.readUTF();
				if (receivedFromClient.equals("Feedback:")) {
					System.out.println("Feedback received");
				}
				else {
//					fixedString = receivedFromClient.substring(1, receivedFromClient.length()-1);
					System.out.println('"' + receivedFromClient + '"');
					DOS.writeUTF('"' + receivedFromClient + '"' + " Received");
				}
				
			}
		}
		
		catch (IOException io) {
			io.printStackTrace();
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
}
