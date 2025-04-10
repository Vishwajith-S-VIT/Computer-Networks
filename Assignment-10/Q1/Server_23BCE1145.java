import java.net.*;

public class Server_23BCE1145{
    public static int LengthOfNumber(int n){
        int l = 0;
        while(n!=0){
            l++;
            n /= 10;
        }
        return l;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args){
        int serverPort = 12345;
        try{
            DatagramSocket serverSocket = new DatagramSocket(serverPort);
            System.out.println("UDP Server is running on the port: " + serverPort);
            byte[] receiveBuffer = new byte[1024];
            
            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                int n = Integer.parseInt(message);
                System.out.println("Number received from " + receivePacket.getAddress() + ":" + receivePacket.getPort() + " is = " + n);
                
                String response = Integer.toString(LengthOfNumber(n));
                byte[] sendBuffer = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}