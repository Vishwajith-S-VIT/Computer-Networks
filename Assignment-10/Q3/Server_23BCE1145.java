import java.net.*;
import java.util.*;

@SuppressWarnings("unused")
public class Server_23BCE1145 {
    public static void main(String[] args) {
        final int SERVER_PORT = 12345;
        DatagramSocket serverSocket = null;
        Scanner sc = new Scanner(System.in);
        String divisor = "10011";
        try {
            serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("UDP Server is running on port " + SERVER_PORT);
            while (true) {
                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (receivedMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client has exited. Shutting down server.");
                    break;
                }
                receivedMessage = xor(receivedMessage, "0".repeat(receivedMessage.length() - 4) + "1010");
                System.out.println("Received Codeword:" + receivedMessage);
                String r = divide(receivedMessage, divisor).substring(1);
                System.out.println("Remainder: " + r);
                if (r.equals("0".repeat(divisor.length() - 1))) {
                    System.out.println("No Error");
                    System.out.println(
                            "Message from Client:" + receivedMessage.substring(0, receivedMessage.length() - 4));
                } else {
                    System.out.println("Error Detected");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
            sc.close();
        }
    }

    public static String divide(String dividend, String divisor) {
        String q = "", r = "";
        int n = dividend.length() - divisor.length();
        for (int i = 0; i < n + 1; i++) {
            if (dividend.charAt(i) == '0') {
                q += '0';
                r = xor(dividend.substring(i, i + divisor.length()), "0".repeat(divisor.length()));
            } else {
                q += '1';
                r = xor(dividend.substring(i, i + divisor.length()), divisor);
                dividend = dividend.substring(0, i) + r + dividend.substring(i + divisor.length());
            }
        }
        return r;
    }

    public static String xor(String a, String b) {
        String c = "";
        for (int i = 0; i < a.length(); i++) {
            int a1 = Integer.parseInt(a.charAt(i) + "");
            int b1 = Integer.parseInt(b.charAt(i) + "");
            c += (a1 ^ b1) + "";
        }
        return c;
    }
}
