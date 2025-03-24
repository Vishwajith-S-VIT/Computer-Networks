import java.net.*;
import java.util.*;
import java.io.*;

@SuppressWarnings("deprecation")
public class Client_23BCE1145{
	private Socket socket = null;
	private DataInputStream din = null;
	private DataInputStream in = null;
	private DataOutputStream dout = null;
	private int m = 5;
	private int ws = (int) Math.pow(2, m - 1);
	private HashMap<Integer, String> windows = new HashMap<Integer, String>(ws);
	private Set<Integer> acks = Collections.synchronizedSet(new HashSet<Integer>());
	public Client_23BCE1145(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
			din = new DataInputStream(System.in);
			dout = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} 
		catch (Exception u) {
			System.out.println(u);
			return;
		}
	}

	public void Work() {
		String line = "";
		int fc = 0;
		try {
			while (true) {
				System.out.println("\nEnter the message to send: ");
				line = din.readLine();
				System.out.println("\nClient is sending: " + line);
				if (line.equalsIgnoreCase("end")) {
					System.out.println("Closing Connection");
					dout.writeUTF("End");
					dout.flush();
					Thread.sleep(1000);
					closeConnections();
					return;
				}

				String[] arr = line.split(" ");
				int totalFrames = arr.length;

				for (int i = 0; i < totalFrames; i++) {
					windows.put(i, arr[i]);
					acks.add(i);
				}
				new Thread(() -> receiveACKs()).start();
				while (!acks.isEmpty()) {
					for (int i = 0; i < ws && fc < totalFrames; i++) {
						if (acks.contains(fc)) {
							//if (fc!=3)
							send(fc);
						}
						fc++;
					}

					Thread.sleep(5000);

					for (int index : new ArrayList<>(acks)) {
						System.out.println("Timeout for Frame " + index + ", resending...");
						send(index);
					}
				}
			}
		} 
		catch (Exception t) {
			System.out.println(t);
		} 
		finally {
			closeConnections();
		}
	}

	public void send(int i) {
		System.out.println("Sending Frame " + (i+1) + " with Seq No. " + (i % (ws * 2)));
		try {
			dout.writeUTF(windows.get(i) + " " + (i % (ws * 2)));
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void receiveACKs() {
		try {
			while (!acks.isEmpty()) {
				String ack = in.readUTF();
				int ackNum = Integer.parseInt(ack);
				if (acks.contains(ackNum)) {
					System.out.println("Ack " + ackNum + " received");
					acks.remove(ackNum);
				}
			}
		} 
		catch (IOException e) {
			System.out.println(e);
		}
	}

	public void closeConnections() {
		try {
			socket.close();
			din.close();
			dout.close();
		} 
		catch (IOException t) {
			System.out.println(t);
		}
	}

	public static void main(String args[]) {
		Client_23BCE1145 client = new Client_23BCE1145("127.0.0.1", 5000);
		client.Work();
	}
}
