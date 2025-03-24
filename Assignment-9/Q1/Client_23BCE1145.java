import java.net.*;
import java.io.*;

@SuppressWarnings("unused")
public class Client_23BCE1145{
    private Socket socket = null;
    private DataInputStream in = null;
    private DataInputStream din = null;
    private DataOutputStream dout = null;

    public Client_23BCE1145(String address, int port) {
        try{
            socket = new Socket(address, port);
            System.out.println("Connected to Server.\n");

            in = new DataInputStream(System.in);
            dout = new DataOutputStream(socket.getOutputStream());
            din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public void Work(){
        try{
            String ip = "14.24.74.0";
            int cidr = 24; 
            int num = 3;
            int[] arrIps = {10,60,120};

            StringBuilder request = new StringBuilder();
            request.append(ip+" "+cidr+" "+num);
            for (int i : arrIps) {
                request.append(" ").append(i);
            }
            dout.writeUTF(request.toString());
            String response = din.readUTF();
            System.out.println("Subnet details: \n" + response);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally{
            System.out.println("\nClosing connection.");
            try{
                socket.close();
                din.close();
                dout.close();
            }
            catch(IOException t){
                System.out.println(t);
            }
        }
    }

    public static void main(String args[]) {
        Client_23BCE1145 client = new Client_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}
