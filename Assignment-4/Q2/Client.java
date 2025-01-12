import java.io.*;
import java.net.*;

public class Client{
    private static Socket socket;
    private static DataInputStream In;
    private static DataOutputStream Out;
    public static void main(String [] args) throws IOException {
        String n = args[0];
        String base = args[1];
        socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 5001), 1000);
        System.out.println("Connection Successful!");
        In = new DataInputStream(socket.getInputStream());
        Out = new DataOutputStream(socket.getOutputStream());
        Out.writeUTF(n);
        Out.writeUTF(base);
        String line = In.readUTF();
        System.out.println("Result: " + line);
    }
}