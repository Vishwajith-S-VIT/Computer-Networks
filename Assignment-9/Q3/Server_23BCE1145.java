import java.io.*;
import java.net.*;

class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private BufferedReader input = null;
    private int portNum = -1;
    private int Rn = 0;
    public Server(int port) {
        this.portNum = port;
    }

    //"Welcome to Networks lab - Error and flow control in selective repeat protocol"
    
    void Work() {
        try {
            server = new ServerSocket(portNum);
            System.out.println("Server started.\nWaiting for a client...");

            socket = server.accept();
            System.out.println("Client connected.\n");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage;
            while (true) {
                try {
                    clientMessage = in.readUTF();
                    System.out.println("Received: Message = \"" + clientMessage + "\"");

                    if (clientMessage.equalsIgnoreCase("end")) {
                        System.out.println("Client ended the session.");
                        break;
                    }


                    int receivedSeqNum = Integer.parseInt(clientMessage.split(": ")[1]);

                    if (receivedSeqNum == Rn) {
                        System.out.println("Correct packet received. Delivering data...");
                        Rn++;
                        out.writeUTF("ACK: " + Rn);
                        System.out.println("Rn = "+Rn);
                    } else {
                        System.out.println("Out-of-order packet received. Discarding...");
                    }

                } catch (IOException e) {
                    System.out.println("Error: " + e);
                    break;
                }
            }

            System.out.println("\nClosing connection...");
            socket.close();
            in.close();
            out.close();
            server.close();
            input.close();
        } catch (IOException e) {
            System.out.println("Server error: " + e);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
        server.Work();
    }
}

/*public class Server_23BCE1145 {
    public static void main(String args[]) {
        Server server = new Server(5000);
        server.Work();
    }
}*/