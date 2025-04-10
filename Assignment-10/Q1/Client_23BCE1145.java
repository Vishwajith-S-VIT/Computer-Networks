import java.net.*;
import java.util.*;

public class Client_23BCE1145{
    public static void main(String[] args){
        String serverIP = "127.0.0.1";
        int serverPort = 12345;
        try{
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(serverIP);
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print("Enter a number: ");
                String inp = sc.nextLine();
                
                if(inp.equalsIgnoreCase("end")){
                    break;
                }
                if(inp.matches("[0-9]*")){
                    byte[] sendBuffer = inp.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
                    clientSocket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    clientSocket.receive(receivePacket);

                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Server: " + response);
                }
                else{
                    System.out.println("The entered value isn't a number. Please try again.");
                }
            }
            clientSocket.close();
            sc.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}