import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	ServerSocket server;
	Socket connection;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public void runServer() throws IOException {
		server = new ServerSocket(1234);
		while(true) {
			try {
				standby();
				setupStreams();
			}
			catch (EOFException e) {
				System.out.println("Server terminated connection\n");
			}
			finally {
				cleanup();
			}
		}
	}
	
	// waiting for client to connect
	private void standby() throws IOException {
		System.out.println("Waiting for connection...\n");
		connection = server.accept();
	}
	
	// sets up stream between client and server
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		System.out.println("Streams are now setup!\n");
	}
	
	// housekeeping method when connection is terminated
	private void cleanup() {
		System.out.println("Connection terminating...\n");
		try {
			input.close();
			output.close();
			connection.close();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	private void test1() {
		try {
			output.writeObject("yoyoyo");
			output.flush();
		}
		catch (IOException i) {
			System.out.println("Error: failed to output to client");
		}
	}
}
