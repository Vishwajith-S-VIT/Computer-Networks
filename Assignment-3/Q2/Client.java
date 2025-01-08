import java.io.*;
import java.net.*;

public class Client {
	
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	public Client(String address, int port)
	{
		
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			input = new DataInputStream(System.in);

			out = new DataOutputStream(
				socket.getOutputStream());
		}
		catch (UnknownHostException u) {
			System.out.println(u);
			return;
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}
	}

	public void work(){
		String line = "";
		String line2 = "";
		
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            out.writeUTF(ipAddress);
            line = in.readUTF();
            System.out.println(line);
            line2 = input.readUTF();
            out.writeUTF(line2);
		}
		catch (UnknownHostException u) {
			System.out.println(u);
			return;
		}
		catch (IOException u) {
			System.out.println(u);
			return;
		}

		
		try {
			input.close();
			out.close();
			socket.close();
			in.close();
		}
		catch (IOException i) {
			System.out.println(i);
		}
	}

    public static void main(String args[])
	{
		Client client = new Client("127.0.0.1", 5000);
		client.work();
	}
}