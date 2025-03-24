import java.io.*;
import java.net.*;

public class Client_23BCE1145{

    private static Socket socket;
    private static DataInputStream dataIn;
    private static DataOutputStream dataOut;
    public static void main(String [] args) throws IOException {
        String ip = "192.17.21.26";
        String packet = args[0];
        socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 5001), 1000);
        System.out.println("Connection Successful!\n");

        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());

        dataOut.writeUTF(ip);
        dataOut.writeUTF(packet);

        String serverMessage = dataIn.readUTF();
        System.out.println("Subnet information: " + serverMessage);

        dataIn.close();
        dataOut.close();
        socket.close();
        System.out.println("\nConnection Closed!");
    }
}