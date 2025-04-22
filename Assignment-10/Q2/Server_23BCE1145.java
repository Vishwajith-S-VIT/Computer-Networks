import java.net.*;
import java.util.*;

public class Server_23BCE1145 {
    public static void main(String[] args) {
        final int SERVER_PORT = 12345;
        DatagramSocket serverSocket = null;
        Scanner sc = new Scanner(System.in);
        try {
            serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("UDP Server is running on port " + SERVER_PORT);
            while (true) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (receivedMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client has exited. Closing the server.");
                    break;
                }
                System.out.println("Message from Client: " + receivedMessage);
                System.out.print("Enter message (type 'Exit' to quit): ");
                String msg = sc.nextLine();
                byte[] sendBuffer = msg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
            sc.close();
        }
    }
}
