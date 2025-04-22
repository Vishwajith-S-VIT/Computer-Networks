import java.net.*;
import java.util.*;

@SuppressWarnings("unused")
public class Server_23BCE1145 {
    private static int m = 3;
    private static int ws = (int) Math.pow(2, m - 1);
    private static HashMap<Integer, String> receivedFrames = new HashMap<>();

    public static void main(String[] args) {
        final int SERVER_PORT = 12345;
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("UDP Server is running on port " + SERVER_PORT);

            while (true) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (receivedMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Closing server.");
                    break;
                }

                String[] parts = receivedMessage.split(" ");
                String frameData = parts[0];
                int seq = Integer.parseInt(parts[1]);

                if (!receivedFrames.containsKey(seq)) {
                    receivedFrames.put(seq, frameData);
                    System.out.println("Received Frame with Seq No. " + seq + ": " + frameData);
                    byte[] sendBuffer = String.valueOf(seq).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                            receivePacket.getAddress(), receivePacket.getPort());
                    if (seq != 4) // Comment for no ack loss
                        serverSocket.send(sendPacket);
                    System.out.println("ACK " + seq + " sent to client.");
                } else {
                    System.out.println("Duplicate Frame " + seq + " discarded.");
                    byte[] sendBuffer = String.valueOf(seq).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer,
                            sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null)
                serverSocket.close();
        }
    }
}
