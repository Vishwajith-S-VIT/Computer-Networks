import java.io.*;
import java.net.*;

public class Client{
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    public Client(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Client is connected!\nSending the Wesbite address to the server...\n");
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
    @SuppressWarnings("deprecation")
    public void Work(){
        try{
            URL url = new URL("https://vishwajith-s-vit.github.io/");
            output.writeUTF(url.toString());
            String line = input.readUTF();
            System.out.println("The IP Address of the Website is: " + line);
        }
        catch(MalformedURLException m){
            System.out.println(m);
        }
        catch(IOException i){
            System.out.println(i);
        }

        System.out.println("\nClosing the connection...");
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
        Client client = new Client("127.0.0.1", 5000);
        client.Work();
    }
}