import java.io.*;
import java.net.*;

public class Server_23BCE1145{
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Server_23BCE1145(int port){
        try{
            System.out.println("\nStarting the server...");
            server = new ServerSocket(port);
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted.\n");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

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
            String[] data = new String[9];
            while(true){
                String[] frame = in.readUTF().split(" ");
                if(frame[1].equalsIgnoreCase("End")){
                    data[Integer.parseInt(frame[0])] = frame[1];
                    break;
                }
                data[Integer.parseInt(frame[0])] = frame[1];
                System.out.println("Frame " + frame[0] + " received. Data is: \"" + frame[1] + "\".\nSending Acknowledgement " + frame[0] + ".");
                out.writeUTF("Acknowledged " + frame[0]);
            }
            System.out.print("\nThe complete data is: ");
            for(int j = 0; j < 8; j++){
                System.out.print(data[j] + " ");
            }
            System.out.println();
        }
        catch(IOException i){
            System.out.println(i);
        }

        try{
            System.out.println("\nClosing connection...");
            in.close();
            out.close();
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