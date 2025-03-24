import java.io.*;
import java.net.*;

@SuppressWarnings("unused")
class Client {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private BufferedReader input = null;
    private String address = "";
    private int port = -1;

    public Client(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void Work() {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to server.\n");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter the Message: ");
            String Input_Msg = input.readLine();
            String[] messages = Input_Msg.split(" ");
            int Ssize = 4;
            String line = "";
            int Sf = 0, Sn = 0;
            boolean flag = false;
            long currTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - currTime < 2000) { 
                while (Sn - Sf + 1 <= Ssize && Sn < messages.length) { 
                    if(Sn==1 && !flag){
                        System.out.println("Sending packet (Sn) " + Sn + ": " + messages[Sn]+" -- (lost)");
                        Sn++;
                        flag = true;
                        break;
                    }
                    System.out.println("Sending packet (Sn) " + Sn + ": " + messages[Sn]);
                    out.writeUTF(messages[Sn]+": "+Sn); 
                    out.flush();
                    Sn++;
                }
            
                try {
                    socket.setSoTimeout(1000);
                    String ack = in.readUTF(); 
                    if (ack.equals("ACK: " + (Sf+1))) {
                        System.out.println("Received ACK for packet (Sf)" + Sf);
                        Sf++;
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout! Resending unacknowledged packets...");
                    for (int i = Sf; i < Sn; i++) {
                        System.out.println("Resending packet " + i + ": " + messages[i]);
                        out.writeUTF(messages[i]+": "+i);
                        out.flush();
                    }
                }
            }
            
            
            System.out.println("\nAll packets acknowledged. Closing connection...");
            out.writeUTF("end");
            out.flush();

            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Client error: " + e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(5000, "127.0.0.1");
        client.Work();
    }
}

/*public class Client_23BCE1145{
    public static void main(String[] args) {
        Client client = new Client(5000, "127.0.0.1");
        client.Work();
    }
}*/