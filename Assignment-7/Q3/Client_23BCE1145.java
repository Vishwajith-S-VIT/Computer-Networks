import java.net.*;
import java.io.*;

public class Client_23BCE1145{ 
	private Socket socket=null;
	private DataInputStream input=null;
	private DataOutputStream output=null;

	public Client_23BCE1145(String address,int port){ 
		try{
			System.out.println("Client has been started.");
			socket=new Socket(address,port);
			System.out.println("Connected to the server.\n");
			input=new DataInputStream(System.in);
			output=new DataOutputStream(socket.getOutputStream());
		} 
		catch(UnknownHostException u){ 
			System.out.println(u);
		return;
		} 
		catch(IOException u){ 
			System.out.println(u);
		return;
		}  
	}

	@SuppressWarnings("deprecation")
	public void Work(){
		String line="";
		while (!line.equalsIgnoreCase("END")){ 
			try{ 
				line=input.readLine();
				if (line.equalsIgnoreCase("END")){
					break;
				}
				int len=line.length();
				int p=0;
				for (int i=1;i<len;i++){
					if (Math.pow(2,i)>=(len+i+1)){
						p=i;
						break;
					}
				}
				line=new StringBuilder(line).reverse().toString();
				System.out.println(len+p);
				for (int i=1;i<(len+p);i=i*2){
					line=line.substring(0,i-1)+'0'+line.substring(i-1,line.length());
				}
				for (int i=0;i<p;i++){
					int pa=Even_Parity(line,i);
					System.out.println("P"+(int)Math.pow(2,i)+": "+pa);
					line=line.substring(0,(int)Math.pow(2,i)-1)+pa+line.substring((int)Math.pow(2,i),line.length());
				}
				line=new StringBuilder(line).reverse().toString();
				System.out.println("Code Word: "+ line + ".\n");
				output.writeUTF(line);
				
			} 
			catch(IOException i){ 
				break;
			} 
		} 
		try{
			System.out.println("Closing Connection.");
			socket.close();
			input.close();
			output.close();
		} 
		catch(IOException i){ 
			System.out.println(i);
		}
	}

	public static void main(String args[]){ 
		Client_23BCE1145 client=new Client_23BCE1145("127.0.0.1",5000);
		client.Work();
	} 
	public static int Even_Parity(String line,int index){
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
