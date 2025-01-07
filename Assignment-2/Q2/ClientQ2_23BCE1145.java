import java.io.*;
import java.net.*;

public class ClientQ2_23BCE1145{
    private Socket socket = null;
    private DataInputStream input1 = null;
    private DataInputStream input2 = null;
    private DataOutputStream output1 = null;
    public ClientQ2_23BCE1145(String address, int port){
        try{
            System.out.println("Connecting to the server.");
            socket = new Socket(address, port);
            input1 = new DataInputStream(System.in);
            output1 = new DataOutputStream(socket.getOutputStream());
            input2 = new DataInputStream(socket.getInputStream());
            System.out.println("Enter the inputs. \nType \"end\" to finish:\n");
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
                System.out.print("Client: ");
                line = input1.readLine();
                output1.writeUTF(line);
                if(line.equalsIgnoreCase("end")){
                    break;
                }
                line = input2.readUTF();
                System.out.println("Server: " + line);
                if(line.equalsIgnoreCase("end")){
                    break;
                }
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        try{
            System.out.println("\nClosing the client.");
            input1.close();
            output1.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        ClientQ2_23BCE1145 client = new ClientQ2_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}
