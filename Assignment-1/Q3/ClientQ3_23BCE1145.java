import java.io.*;
import java.net.*;

public class ClientQ3_23BCE1145 {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private DataInputStream input2 = null;

    public ClientQ3_23BCE1145(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Client is connected!\n\nType 'End' to stop the programs.\n");
            input = new DataInputStream(System.in);
            output = new DataOutputStream(socket.getOutputStream());
            input2 = new DataInputStream(socket.getInputStream());
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
        String line = "", l = "";
        while(!line.equalsIgnoreCase("end")){
            try{
                line = input.readLine();
                output.writeUTF(line);
                l = input2.readUTF();
                System.out.println(l);
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        
        try{
            System.out.println("\nClosing the client.");

            input.close();
            output.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public static void main(String[] args){
        ClientQ3_23BCE1145 client  = new ClientQ3_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}
