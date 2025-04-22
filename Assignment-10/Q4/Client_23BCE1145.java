import java.net.*;
import java.util.*;
import java.io.*;

public class Client_23BCE1145 {
    private static int m = 3;
    private static int ws = (int) Math.pow(2, m - 1);
    private static HashMap<Integer, String> windows = new HashMap<>();
    private static Set<Integer> acks = Collections.synchronizedSet(new HashSet<>());
    private static DatagramSocket clientSocket;
    private static InetAddress serverAddress;

    public static void main(String[] args) {
        final int SERVER_PORT = 12345;
        Scanner scanner = new Scanner(System.in);

        try {
            clientSocket = new DatagramSocket();
            serverAddress = InetAddress.getLocalHost();

            while (true) {
                System.out.print("Enter message (type 'exit' to quit): ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    byte[] sendBuffer = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress,
                            SERVER_PORT);

                    clientSocket.send(sendPacket);
                    break;
                }

                String[] arr = message.split(" ");
                int totalFrames = arr.length;
                for (int i = 0; i < totalFrames; i++) {
                    windows.put(i, arr[i]);
                    acks.add(i % (ws * 2));
                }
                new Thread(() -> receiveACKs()).start();

                int fc = 0;
                while (!acks.isEmpty()) {
                    for (int i = 0; i < ws * 2 && fc < totalFrames; i++) {
                        if (acks.contains(fc)) {
                            if (fc != 2) // Comment for no data loss
                                send(fc);
                        }
                        fc++;
                    }
                    Thread.sleep(3000);
                    resendUnacknowledgedFrames();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            clientSocket.close();
            scanner.close();
        }
    }

    public static void send(int i) {
        try {
            String msg = windows.get(i) + " " + (i % (ws * 2));
            byte[] sendBuffer = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 12345);
            clientSocket.send(sendPacket);
            System.out.println("Sent Frame " + (i + 1) + " with Seq No. " + (i % (ws * 2)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void receiveACKs() {
        try {
            while (!acks.isEmpty()) {
                byte[] ackBuffer = new byte[1024];
                DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length);
                clientSocket.receive(ackPacket);

                String ack = new String(ackPacket.getData(), 0, ackPacket.getLength());
                int ackNum = Integer.parseInt(ack);

                if (acks.contains(ackNum)) {
                    System.out.println("Ack " + ackNum + " received");

                    acks.remove(ackNum);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void resendUnacknowledgedFrames() {
        for (int index : new ArrayList<>(acks)) {
            if (acks.contains(index)) {
                System.out.println("Timeout for Frame " + index + ", resending...");
                send(index);
            }
        }
    }

}
