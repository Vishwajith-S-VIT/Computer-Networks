import java.io.*;
import java.net.*;
public class Server{
	@SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5001);
		System.out.println("Server Listening on port 5001...");
		while (true) {
			Socket connectionSocket = serverSocket.accept();
			new Thread(() -> {
				try {
					DataInputStream dataIn = new DataInputStream(connectionSocket.getInputStream());
					DataOutputStream dataOut = new DataOutputStream(connectionSocket.getOutputStream());
					String str = dataIn.readUTF();
					String op = dataIn.readUTF();
					
					switch(op){
						case "Length":
							dataOut.writeUTF(""+str.length());
							break;
						case "Upper":
							dataOut.writeUTF(str.toUpperCase());
							break;
						case "Lower":
							dataOut.writeUTF(str.toLowerCase());
							break;
						case "Append":
							String str2 = "";
							for(int i = 0;i<str.length();i++){
								if(i%2==0){
									str2 += Character.toUpperCase(str.charAt(i));	
								}
								else{
									str2 += str.charAt(i);
								}
							}
							dataOut.writeUTF(str2);
							break;
						case "Reverse":
							String str3 = "";
                            for(int i = str.length() - 1; i > -1; i--){
                                str3 += str.charAt(i);
                            }
							dataOut.writeUTF(str3);
							break;
						default:
							dataOut.writeUTF("Invalid Operation");
							break;
					}
					connectionSocket.close();
				}
                catch(IOException e){
					System.out.println(e);
				}
			}).start();
		}
	}
}