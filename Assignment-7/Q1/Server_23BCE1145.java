import java.io.*;
import java.net.*;


public class Server_23BCE1145 {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    public Server_23BCE1145(int port){
        try{
            System.out.println("\nStarting server.");
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
        try{
            int srn = Integer.parseInt(input.readUTF());
            int cc = Integer.parseInt(input.readUTF());
            int mo = Integer.parseInt(input.readUTF());
            int ap = Integer.parseInt(input.readUTF());
            int res1 = Integer.parseInt(input.readUTF());
            int tot = srn + cc + mo + ap, bits = 12;
            int rem = (tot >> bits) & 0b11;
            int rem2 = tot & 0xFFF;
            int res2 = ~(rem + rem2 + res1) & 0xFFF;
            System.out.println("Student Register Number: " + srn + ".");
            System.out.println("Course Code: " + cc + ".");
            System.out.println("Marks Obtained: " + mo + ".");
            System.out.println("Attendance Percentage: " + ap + ".\n");
            if(res2 == 0){
                System.out.println("Checksum verification passed.\n");
            }
            else{
                System.out.println("Checksum verficiation failed.\n");
            }
        }
        catch(IOException i){
            System.out.println(i);
        }
        try{
            System.out.println("Closing connection.");
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
