import java.io.*;
import java.net.*;

public class ServerQ2_23BCE1145{
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input1 = null;
    private DataInputStream input2 = null;
    private DataOutputStream output1 = null;
    public ServerQ2_23BCE1145(int port){
        try{
            System.out.println("Starting the server.");
            server = new ServerSocket(port);
            System.out.println("Waiting for Client.");
            socket = server.accept();
            System.out.println("Client is connected.\n");
            input1 = new DataInputStream(socket.getInputStream());
            input2 = new DataInputStream(System.in);
            output1 = new DataOutputStream(socket.getOutputStream());
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
        while(true){
            try{
                line = input1.readUTF();
                System.out.println("Client: " + line);
                if(line.equalsIgnoreCase("end")){
                    break;
                }
                System.out.print("Server: ");
                line = input2.readLine();
                output1.writeUTF(line);
                if(line.equalsIgnoreCase("end")){
                    break;
                }
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        try{
            System.out.println("\nClosing the server.");
            input1.close();
            socket.close();
            server.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        ServerQ2_23BCE1145 server = new ServerQ2_23BCE1145(5000);
        server.Work();
    }
}