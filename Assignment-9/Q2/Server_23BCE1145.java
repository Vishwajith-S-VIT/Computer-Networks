import java.net.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class Server_23BCE1145{
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream din = null;
	private DataOutputStream dout = null;
	private int m = 5;
	private int ws = (int) Math.pow(2, m - 1);
	private HashMap<Integer, String> receivedFrames = new HashMap<Integer, String>();

	public Server_23BCE1145(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server started\nWaiting for a client...");
			socket = server.accept();
			System.out.println("Client accepted.\n");
			din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dout = new DataOutputStream(socket.getOutputStream());
			receiveFrames();
			socket.close();
			din.close();
			dout.close();
		} 
		catch (IOException u) {
			System.out.println(u);
		}
	}

	public void receiveFrames() {
		String line = "";
		int expectedSeq = 0;

		while (true) {
			try {
				line = din.readUTF();
				if (line.equalsIgnoreCase("end")){
					System.out.println("\nClosing the server.");
					return;
				} 
				String[] parts = line.split(" ");
				String f = parts[0];
				int seq = Integer.parseInt(parts[1]);
				if (!receivedFrames.containsKey(seq)) {
					receivedFrames.put(seq, f);
					System.out.println("Received Frame with Seq No. " + seq + " and message: \"" + f + "\"");
					if (seq==6){
						new Thread(()->{
							try{
								Thread.sleep(6000);
								dout.writeUTF(seq + "");
							}
							catch(Exception e){
								System.out.println(e);
							}
						}).start();
					}
					else{
						dout.writeUTF(seq + "");
					}
					
					expectedSeq = (expectedSeq + 1) % (ws * 2);
				} 
				else {
					System.out.println("Duplicate Frame " + seq + " discarded.");
				}
			} 
			catch (EOFException e) {
				System.out.println("Client Disconnected.");
				break;
			}
			catch (IOException e) {
				System.out.println(e);
				break;
			}
		}
	}

    public static void main(String args[]) {
		Server_23BCE1145 server = new Server_23BCE1145(5000);
	}
}
