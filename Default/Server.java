import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    public Server(int port){
        try{
            System.out.println("Starting server.\n");
            server = new ServerSocket(port);
            System.out.println("Waiting for a connection.\n");
            socket = server.accept();
            System.out.println("Connection with client established.\n");
            input = new DataInputStream(socket.getInputStream());
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
        while(!line.equalsIgnoreCase("end")){
            try{
                line = input.readUTF();
                System.out.println(line);
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        System.out.println("\nClosing Connection.");
        try{
            input.close();
            socket.close();
            server.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        Server server = new Server(5000);
        server.Work();
    }
}
