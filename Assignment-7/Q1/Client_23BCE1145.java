import java.io.*;
import java.net.*;

public class Client_23BCE1145{
    private Socket socket = null;
    private DataOutputStream output = null;
    public Client_23BCE1145(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Client is connected!\n");
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public void Work(){
        int srn = 8942, cc = 1308, mo = 85, ap = 92;
        int tot = srn + cc + mo + ap, bits = 12;
        int rem = (tot >> bits) & 0b11;
        int rem2 = tot & 0xFFF;
        int res = ~(rem + rem2) & 0xFFF;
        try{
            output.writeUTF(""+srn);
            output.writeUTF("1318");
            output.writeUTF(""+mo);
            output.writeUTF(""+ap);
            output.writeUTF(""+res);
            System.out.println("Student Register Number: " + srn + ".");
            System.out.println("Course Code: " + cc + ".");
            System.out.println("Marks Obtained: " + mo + ".");
            System.out.println("Attendance Percentage: " + ap + ".\n");
            System.out.println("Calculated checksum is: " + res + ".\n");
        }
        catch(IOException i){
            System.out.println(i);
        }
        try{
            System.out.println("Closing connection.");
            output.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        Client_23BCE1145 client = new Client_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}