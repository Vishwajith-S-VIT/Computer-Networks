import java.net.*;
import java.io.*;

public class Server
{
	
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	public Server(int port)
	{
		try{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		}
		catch(IOException i){
			System.out.println(i);
		}
	}

	public void work(){
		String line = "";

			String arr[] = {"127.0.1.1","127.0.1.2","127.0.1.3","127.0.1.4"};
			while (!line.equals("End")){
				try{
					line = in.readUTF();
					for(int i = 0;i<4;i++){
						if(line.equals(arr[i])){
							out.writeUTF("Hello");
						}
					}

				}
				catch(IOException i){
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");

			try{
				socket.close();
				in.close();
				out.close();
			}
			catch(IOException i){
				System.out.println(i);
			}
	}

    public static void main(String args[])
	{
		Server server = new Server(5000);
		server.work();
	}
}