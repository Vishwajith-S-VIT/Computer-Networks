import java.net.*;
import java.io.*;
 
public class Server
{
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
 
	
	public Server(int port)
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");
 
			System.out.println("Waiting for a client ...");
 
			socket = server.accept();
			System.out.println("Client accepted");
 
			
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));
 
			String line = "";
 
			
			while (!line.equals("End"))
			{
				try
				{
					line = in.readUTF();
					
					//Error creation code
					line = line.substring(0,14)+"0"+line.substring(15); //Converts E to A
					
					for(int j=0;j<6;j++){
						int q=0;
						for(int i=8*j;i<8*(j+1);i++){
							if(line.charAt(i)=='1'){
								q++;
							}
						}
						if(q%2!=0){
							System.out.println("Error in transmission of character "+(j+1));
						}
					}
 
				}
				catch(IOException i)
				{
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");
 
			socket.close();
			in.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}
 
	@SuppressWarnings("unused")
    public static void main(String args[])
	{
		Server server = new Server(5000);
	}
}