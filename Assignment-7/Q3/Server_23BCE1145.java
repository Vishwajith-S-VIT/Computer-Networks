import java.net.*;
import java.io.*;

public class Server_23BCE1145{ 
	private Socket socket=null;
	private ServerSocket server=null;
	private DataInputStream input=null;

	public Server_23BCE1145(int port){ 
		try{ 
			server=new ServerSocket(port);
			System.out.println("Server Started.\nWaiting for a client...");
			socket=server.accept();
			System.out.println("Client accepted.\n");
			input=new DataInputStream(socket.getInputStream());
			String line="";
			while (!line.toUpperCase().equalsIgnoreCase("END")){ 
				try{ 
					line=input.readUTF();
					if (line.equalsIgnoreCase("END")){ 
						break;	
					} 
					line=line.substring(0,6)+(Integer.parseInt(line.charAt(6)+"")^1)+line.substring(7,line.length());
					System.out.println("Received Codeword: "+line);
					int len=line.length();
					String par="";
					line=new StringBuilder(line).reverse().toString();
					for (int i=0;(int)Math.pow(2,i)<len;i++){
						int pa=parity(line,i);
						System.out.println("P"+(int)Math.pow(2,i)+": "+pa);
						par=pa+par;
					}
					int bin=Integer.parseInt(par,2);
					if (bin==0){
						System.out.println("No error detected");
					}
					else{
						System.out.println("The bit number "+bin+" is an error and thus needs to be flipped");
						line=line.substring(0,bin-1)+(Integer.parseInt(line.charAt(bin-1)+"")^1)+line.substring(bin,line.length());
						
					}
					line=new StringBuilder(line).reverse().toString();
					System.out.println("Code Word: " + line + ".\n");
				} 
				catch(IOException i){ 
					break;
				}
			} 
		} 
		catch(IOException i){ 
		System.out.println(i);
		} 

		System.out.println("Closing Connection.");
		try{ 
		socket.close();
		input.close();
		} 
		catch(IOException i){ 
		System.out.println(i);
		} 

	} 
	@SuppressWarnings("unused")
	public static void main(String args[]){ 
		Server_23BCE1145 server=new Server_23BCE1145(5000);
	} 
	public static int parity(String line,int index){
		int p=0;
		for (int i=0;i<line.length();i++){
			String bin=new StringBuilder(Integer.toBinaryString(i+1)).reverse().toString();
			if (bin.length()>index && bin.charAt(index)=='1'){
				p=p^Integer.parseInt(line.charAt(i)+"");
			}
		}
		return p;
	}
}
