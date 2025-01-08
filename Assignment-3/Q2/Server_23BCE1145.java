import java.io.*;
import java.net.*;


public class Server_23BCE1145 {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    public Server_23BCE1145(int port){
        try{
            System.out.println("Starting server.");
            server = new ServerSocket(port);
            System.out.println("Waiting for a connection.");
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
        try{
            line = input.readUTF();
            String[] arr = {"192.166.0.1", "192.168.0.168", "127.0.0.1"};
            boolean flag = false;
            for(String s: arr){
                if(s.equals(line)){
                    flag = true;
                    break;
                }
            }
            if(flag){
                System.out.println("Hello\n");
            }
            else{
                System.out.println("Invalid\n");
            }
        }
        catch(IOException i){
            System.out.println(i);
        }
        System.out.println("Closing Connection...");
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
        Server_23BCE1145 server = new Server_23BCE1145(5000);
        server.Work();
    }
}
