import java.io.*;
import java.net.*;

public class Client_23BCE1145{
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    public Client_23BCE1145(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Client is connected!\nCheck the Server for the IP Address of the Client.\n");
            input = new DataInputStream(System.in);
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
        InetAddress ip = socket.getInetAddress();
        String line = ip.getHostAddress();
        try{
            output.writeUTF(line);
        }
        catch(IOException i){
            System.out.println(i);
        }
        System.out.println("Closing the connection...");
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