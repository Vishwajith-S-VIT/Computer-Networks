import java.net.*;
import java.io.*;

public class Server{
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	@SuppressWarnings("unused")
    public Server(int port){
		try{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			String line = "";
			String msg = "";
			String arr[] = {"127.0.1.1","127.0.1.2","127.0.1.3","127.0.1.4"};
			while (!line.equals("End")){
				try{
					msg = in.readUTF();
					line = in.readUTF();
					
					msg = xor(msg,"0000000001010");
					
					int k=line.length();
					int z = msg.length();
					int l = msg.length()-line.length()+1;
					String r=msg.substring(0,line.length());
					while(k<z){
						if(r.charAt(0)=='1'){
							r = xor(line,r);
						}else{
							r = xor("00000",r);
						}
						r+=msg.charAt(k);
						r=r.substring(1);
						k++;
					}
					if(r.charAt(0)=='1'){
						r = xor(line,r);
					}else{
						r = xor("00000",r);	
					}
					System.out.println(msg);
					if(r.equals("00000")){
						System.out.println("No error");
					}else{
						System.out.println("Error");
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
			out.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}
	public String xor(String a,String b){
		String c = "";
		for(int i=0;i<a.length();i++){
			if(a.charAt(i)==b.charAt(i)){
				c+="0";
			}
			else{
				c+="1";
			}
		}
		return c;
	}

	@SuppressWarnings("unused")
    public static void main(String args[])
	{
		Server server = new Server(5000);
	}
}