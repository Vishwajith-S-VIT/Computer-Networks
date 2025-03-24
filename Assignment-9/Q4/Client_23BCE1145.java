import java.io.*;
import java.net.*;

public class Client_23BCE1145{

    private static Socket socket;
    private static DataInputStream dataIn;
    private static DataOutputStream dataOut;
    public static void main(String [] args) throws IOException{
        String ip = args[0];
        String cidr = args[1];

        System.out.println("Starting the client...");
        socket = new Socket();

        socket.connect(new InetSocketAddress("127.0.0.1", 5001), 1000);
        System.out.println("Connected to the server.\n");

        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());

        dataOut.writeUTF(ip);
        dataOut.writeUTF(cidr);

        String serverMessage = dataIn.readUTF();
        System.out.println("Subnet information: " + serverMessage);

        dataIn.close();
        dataOut.close();
        socket.close();
        System.out.println("\nConnection closed.");
    }
}