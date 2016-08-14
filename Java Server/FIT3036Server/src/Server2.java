import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	ServerSocket server;
	Socket clientSocket;
	DataInputStream DIS;
	String receivedFromClient;
	
	public void runServer() {
		System.out.println("Waiting for connection...\n");
		try {
			server = new ServerSocket(1234);
			clientSocket = server.accept();
			System.out.println("Client has connected");
			DIS = new DataInputStream(clientSocket.getInputStream());
			receivedFromClient = DIS.readUTF();
			System.out.println(receivedFromClient);
			clientSocket.close();
			server.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		}
	}
}
