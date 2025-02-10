import java.io.*;
import java.net.*;
 
public class Client{
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
 
	@SuppressWarnings("deprecation")
	public Client(String address, int port)
	{
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
 
		
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
 
	
		String line = "";
 		String line2= "";
		String line3= "";
		int arr[] = {0,0,0,0,0,0,0,0};
		while (!line.equals("End")) {
			try {
				line = input.readLine();
				for(int i=0;i<line.length();i++){
					String b = String.format("%7s",Integer.toBinaryString(line.charAt(i))).replace(' ','0');
					int q=0;
					String p="";
					for(int j=0;j<b.length();j++){
						if(b.charAt(j) == '1'){
							q++;
							arr[j] += 1;
						}
						
					}
					if(q%2==0){
						p = "0";
					}
					else{
						p = "1";
					}
					line2 += (b+p);
				}
				for(int i=0;i<arr.length;i++){
					if(arr[i]%2==0){
						line3 += "0";
					}
					else{
						line3 += "1";
					}
				}
				line3 += "0";
				line2 += line3;
				for(int j=0;j<6;j++){
						for(int i=8*j;i<8*(j+1);i++){
							System.out.print(line2.charAt(i));
						}
						System.out.println("");
					}
				out.writeUTF(line2);
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
 
	@SuppressWarnings("unused")
	public static void main(String args[])
	{
		Client client = new Client("127.0.0.1", 5000);
	}
}