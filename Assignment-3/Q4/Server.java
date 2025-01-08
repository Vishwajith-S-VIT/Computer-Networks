import java.io.*;
import java.net.*;


public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    public Server(int port){
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
    @SuppressWarnings("deprecation")
    public void Work(){
        String line = "";
        try{
            line = input.readUTF();
            InetAddress ip = InetAddress.getByName(new URL(line).getHost());
            System.out.println("The IP Address of the Website is: " + ip.getHostAddress());
            output.writeUTF(ip.getHostAddress());
        }
        catch(MalformedURLException m){
            System.out.println(m);
        }
        catch(IOException i){
            System.out.println(i);
        }
        System.out.println("\nClosing Connection...");
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
