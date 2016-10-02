import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Main.InputHandler;
import Main.Setup;

public class Server {

	ServerSocket server;
	Socket clientSocket;
	DataInputStream DIS;
	DataOutputStream DOS;
	InputHandler ih;
	Setup su = new Setup();
	
	public void runServer() {
		
		try {
			
			// runs the server and waits for client to connect
			server = new ServerSocket(1234);
			System.out.println("Server is now online.\nWaiting for connection on port 1234...");
			
			while (true) {
				// makes connection between client and server
				clientSocket = server.accept();
			
				// sets up input and output streams between client and server
				DIS = new DataInputStream(clientSocket.getInputStream());
				DOS = new DataOutputStream(clientSocket.getOutputStream());
				
				// determine best result and return to client
				ih = new InputHandler(su);
				DOS.writeUTF(ih.handleInput(DIS.readUTF()));
			}
		}
		
		catch (IOException io) {
			io.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
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
