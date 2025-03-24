import java.io.*;
import java.net.*;

@SuppressWarnings({"resource", "unused"})
public class Server_23BCE1145{

    static final String[][] routing_table = {
        {"192.17.0.0", "18", "a"},
        {"192.17.20.0", "22", "b"},
        {"0.0.0.0", "0", "Default"}
    };
    
    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println("Server started on port 5001...");
        while (true){

            Socket connectionSocket = serverSocket.accept();
            new Thread(() -> {
                try{
                    DataInputStream dataIn = new DataInputStream(connectionSocket.getInputStream());
                    DataOutputStream dataOut = new DataOutputStream(connectionSocket.getOutputStream());

                    for(int i=0;i<3;i++){
                        for(int j=i+1;j<3;j++){
                            if(Integer.parseInt(routing_table[i][1]) < Integer.parseInt(routing_table[j][1])){
                                String arr[] = routing_table[i];
                                routing_table[i] = routing_table[j];
                                routing_table[j] = arr;
                            }
                        }
                    }
                    String ip = dataIn.readUTF();
                    String packet = dataIn.readUTF();
                    String line2 = "";
                    for(int i=0;i<3;i++){
                        int n = ipToInt(ip);
                        int mask = 0xFFFFFFFF << (32-Integer.parseInt(routing_table[i][1]));
                        int k = ipToInt(routing_table[i][0]);
                        if((n & mask)==k){
                            line2 += "The longest prefix match is " + routing_table[i][0] + "/" + routing_table[i][1] + " and packet will be sent to interface "+routing_table[i][2];
                            break;
                        }
                    }
                    dataOut.writeUTF(line2);
                    connectionSocket.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public static int ipToInt(String ip) {
        String[] arr = ip.split("\\.");
        int res = 0;
        for (int i=0; i<4; i++) { 
            res |= Integer.parseInt(arr[i]) << ((3-i)*8);
        }
        return res;
    }
    
}
