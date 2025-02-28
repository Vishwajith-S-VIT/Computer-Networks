import java.io.*;
import java.net.*;

public class Client_23BCE1145 {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Client_23BCE1145(String address, int port) {
        try {
            System.out.println("\nConnecting to the server...");
            socket = new Socket(address, port);
            System.out.println("Connected.\n");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } 
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public void Work(){
        try{
            String[] data = "Networks lab is easy to understand and implement. End".split(" ");
            int n = data.length;
            int frameSize = 4, i, j;
            for(i = 0; i < frameSize; i++){
                out.writeUTF(i + " " + data[i]);
            }
            for(i = frameSize; i < n; i++){
                if(data[i].equalsIgnoreCase("End")){
                    out.writeUTF(i + " " + data[i]);
                    break;
                }
                while(true){
                    try{
                        socket.setSoTimeout(3000);
                        String[] ack = in.readUTF().split(" ");
                        if(ack[0].equalsIgnoreCase("Acknowledged") && Integer.parseInt(ack[1]) >= (i - frameSize)){
                            System.out.println("Acknowledgement " + ack[1] + " received. Sending next frame.");
                            for(j = i; j <= Integer.parseInt(ack[1])+frameSize; j++){
                                out.writeUTF(j + " " + data[j]);
                            }
                            break;
                        }
                    }
                    catch(SocketTimeoutException ste){
                        System.out.println("Acknowledgement " + (i - frameSize) + " not received after 3 seconds. Resending Frame " + (i - frameSize + 1) + ".");
                        for(j = i - frameSize; j < i; j++){
                            out.writeUTF(j + " " + data[j]);
                        }
                    }
                }
            }
            i = frameSize;
            while(true){
                boolean flag = false;
                while(true){
                    try{
                        socket.setSoTimeout(3000);
                        String[] ack = in.readUTF().split(" ");
                        if(ack[0].equalsIgnoreCase("Acknowledged")){
                            i = Integer.parseInt(ack[1]);
                            if(i == n-1){
                                flag = true;
                                break;
                            }
                            System.out.println("Acknowledgement " + ack[1] + " received.");
                            break;
                        }
                    }
                    catch(SocketTimeoutException ste){
                        System.out.println("Acknowledgement " + i + " not received after 3 seconds. Resending Frame " + (i - frameSize + 1) + ".");
                        for(j = i; j < n-1; j++){
                            out.writeUTF(j + " " + data[j]);
                        }
                    }
                }
                if(flag){
                    break;
                }
            }
        }
        catch(IOException i){
            System.out.println(i);
        }

        try{
            System.out.println("\nClosing connection...");
            in.close();
            out.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Client_23BCE1145 client = new Client_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}
