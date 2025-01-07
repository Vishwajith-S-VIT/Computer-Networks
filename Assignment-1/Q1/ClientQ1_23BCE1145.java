import java.io.*;
import java.net.*;

class ClientQ1_23BCE1145{
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    public ClientQ1_23BCE1145(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Client is connected!\n");
            input = new DataInputStream(System.in);
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
        while(!line.equalsIgnoreCase("end")){
            try{
                line = input.readLine();
                output.writeUTF(line);
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
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
        ClientQ1_23BCE1145 client = new ClientQ1_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}