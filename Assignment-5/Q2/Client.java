import java.io.*;
import java.net.*;
 
public class Client{
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
 
	@SuppressWarnings("deprecation")
	public Client(String address, int port){
		try{
			socket = new Socket(address, port);
			System.out.println("Connected");
			
			input = new DataInputStream(System.in);
 
			out = new DataOutputStream(
				socket.getOutputStream());
		}
		catch(UnknownHostException u){
			System.out.println(u);
			return;
		}
		catch(IOException i){
			System.out.println(i);
			return;
		}
		
		String line = "";
 		String r = "";
		String res="";
		while (!line.equals("End")) {
			try {
				line = input.readLine();
				String msg = "101101101";
				int g = msg.length();
				for(int i=0;i<line.length()-1;i++){
					msg+="0";
				}
				int z = msg.length();
				int k=line.length();
				r=msg.substring(0,line.length());
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
				res = msg.substring(0,g)+r.substring(1);
				out.writeUTF(res);
				out.writeUTF(line);
				
				System.out.println(res);
				
			}
			catch (IOException i) {
				System.out.println(i);
			}
		}

		try {
			input.close();
			out.close();
			socket.close();
		}
		catch (IOException i) {
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
	public static void main(String args[]){
		Client client = new Client("127.0.0.1", 5000);
	}
}