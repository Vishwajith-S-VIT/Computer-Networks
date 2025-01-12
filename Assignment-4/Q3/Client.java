import java.io.*;
import java.net.*;

public class Client{
  private static Socket socket;
  private static DataInputStream In;
  private static DataOutputStream Out;

  public static void main(String [] args) throws IOException {
    String num1 = args[0];
    String num2 = args[1];
    String operator = args[2];
    
    socket = new Socket();
    socket.connect(new InetSocketAddress("127.0.0.1", 5001), 1000);
    System.out.println("Connection Successful!");
    
    In = new DataInputStream(socket.getInputStream());
    Out = new DataOutputStream(socket.getOutputStream());
    
    Out.writeUTF(num1);
    Out.writeUTF(num2);
    Out.writeUTF(operator);
    
    String line = In.readUTF();
    System.out.println("Result: " + line);
  }
}