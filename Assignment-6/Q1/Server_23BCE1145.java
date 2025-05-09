import java.io.*;
import java.net.*;

public class Server_23BCE1145{
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    public Server_23BCE1145(int port){
        try{
            System.out.println("Starting server.\n");
            server = new ServerSocket(port);
            System.out.println("Waiting for a connection.\n");
            socket = server.accept();
            System.out.println("Connection with client established.\n");
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
        String line = "";
        int frameCount = 0;
        while(!line.equals("End")){
            try{
                line = input.readUTF().split(" ")[1];
                frameCount++;
                if(line.equals("End")){
                    break;
                }
                System.out.println("Frame " + frameCount + " received. Message is: " + line);
                output.writeUTF("Acknowledged");
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
            server.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        Server_23BCE1145 server = new Server_23BCE1145(5000);
        server.Work();
    }
}