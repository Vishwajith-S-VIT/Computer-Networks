import java.math.BigInteger;
import java.net.*;
import java.util.*;

@SuppressWarnings("unused")
public class Client_23BCE1145 {
    public static void main(String[] args) {
        final int SERVER_PORT = 12345;
        String divisor = "10011";
        String r = "";
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getLocalHost();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    byte[] sendBuffer = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress,
                            SERVER_PORT);
                    clientSocket.send(sendPacket);
                    break;
                }
                message = new BigInteger(message.getBytes()).toString(2);
                System.out.println("Data:" + message);
                for (int i = 0; i < divisor.length() - 1; i++)
                    message += '0';
                r = divide(message, divisor).substring(1);
                message = message.substring(0, message.length() - 4) + r;
                System.out.println("Code Word: " + message);
                byte[] sendBuffer = message.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress,
                        SERVER_PORT);
                clientSocket.send(sendPacket);

            }
            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
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
