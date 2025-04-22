import java.net.*;
import java.util.*;

public class Client_23BCE1145 {
    public static void main(String[] args) {
        final int SERVER_PORT = 12345;
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getLocalHost();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter message (type 'exit' to quit): ");
                String message = scanner.nextLine();
                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress,
                        SERVER_PORT);
                clientSocket.send(sendPacket);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (response.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Message from Server: " + response);
            }
            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
