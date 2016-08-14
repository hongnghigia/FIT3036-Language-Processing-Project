import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	ServerSocket server;
	Socket clientSocket;
	DataInputStream DIS;
	DataOutputStream DOS;
	String receivedFromClient;
	String fixedString;
	
	public void runServer() {
		System.out.println("Waiting for connection...\n");
		try {
			// creates the server and waits for a client to connect
			server = new ServerSocket(1234);
			clientSocket = server.accept();
			
			// sets up input and output streams between client and server
			DIS = new DataInputStream(clientSocket.getInputStream());
			DOS = new DataOutputStream(clientSocket.getOutputStream());
			
			// prints what the client sent to the server, and sends to client
			receivedFromClient = DIS.readUTF();
			fixedString = receivedFromClient.substring(1, receivedFromClient.length()-1);
			System.out.println(fixedString);
			DOS.writeUTF('"' + fixedString + '"' + " Received");
			
			// closes connections
			clientSocket.close();
			server.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		} 
	}
}
