import java.io.*;
import java.net.*;

public class Client_23BCE1145 {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    public Client_23BCE1145(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Client is connected!\n");
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public void Work(){
        String[] arr = "Welcome to Computer Networks lab. End".split(" ");
        for(int fc = 0; fc < arr.length; fc++){
            String line = arr[fc];
            if(line.equals("End")){
                try{
                    output.writeUTF(fc + " " + line);
                }
                catch(IOException i){
                    System.out.println(i);
                }
                break;
            }
            try{
                output.writeUTF(fc + " " + line);
                System.out.println("Frame " + (fc) + " sent.");
                String[] ack;
				while(true){
					try {
						socket.setSoTimeout(5000);
						ack=input.readUTF().split(" ");
						if (ack[0].equals("Acknowledged") && ack[1].equals(Integer.toString((fc%2)^1))){
							System.out.println("Acknowledgement " + ((fc%2)^1) + " Received. Sending next frame.");
							break;
						}
					} 
					catch(SocketTimeoutException ste){
						System.out.println("Ackowledgement " + ((fc%2)^1) + " not received after 5 seconds. Resending Frame " + (fc+1) + ".");
						output.writeUTF(fc + " " + line);
					}
				}
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        System.out.println("\nClosing Connection.");
        try{
            input.close();
            output.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public static void main(String[] args){
        Client_23BCE1145 client = new Client_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}
