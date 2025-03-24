import java.io.*;
import java.net.*;
import java.util.*;

@SuppressWarnings("resource")
public class Server_23BCE1145{
    public static void main(String[] args) throws IOException{
        
        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println("Server started on port 5001...");

        long startTime = System.currentTimeMillis();
        while(true){
            if(System.currentTimeMillis() - startTime > 30000){
                System.out.println("No longer accepting clients!");
                break;  
            }

            Socket connectionSocket = serverSocket.accept();
            new Thread(() -> {
                try{
                    DataInputStream dataIn = new DataInputStream(connectionSocket.getInputStream());
                    DataOutputStream dataOut = new DataOutputStream(connectionSocket.getOutputStream());

                    String ip = dataIn.readUTF();
                    int cidr = Integer.parseInt(dataIn.readUTF());

                    String line2 = calcSubnets(ip, cidr);
                    
                    dataOut.writeUTF(line2);
                    connectionSocket.close();
                }
                catch(IOException e){
                e.printStackTrace();
                }
            }).start();
        }
    }

    public static String calcSubnets(String ip, int cidr){
        List<String> subnetInfo = new ArrayList<>();
        String faddr = ip;
        String laddr = lastAddr(ip,cidr);
        int numHosts = (int) Math.pow(2,(32-cidr));
        String mask = SubnetMask(cidr);

        subnetInfo.add(
            " First address: " + faddr +
            " Last address: " + laddr +
            " Number of Hosts: " + numHosts +
            " Subnet Mask: " + mask
            );
        return String.join("\n", subnetInfo);
    }

    public static int ipToInt(String ip){
        String[] arr = ip.split("\\.");
        int res = 0;
        for(int i=0; i<4; i++){ 
            res |= Integer.parseInt(arr[i]) << ((3-i)*8);
        }
        return res;
    }

    public static String intToIp(int ip){
        StringBuilder mask = new StringBuilder();
        for(int i=3; i>=0; i--){
            int s = (ip >> (i*8))&255; 
            mask.append(s);
            if (i > 0) {
                mask.append("."); 
            }
        }
        return mask.toString();
    }

    public static String lastAddr(String ip,int cidr){
        int n = ipToInt(ip);
        int mask = 0xFFFFFFFF << (32-cidr);
        int res = n | ~mask;
        StringBuilder  smask = new StringBuilder();
        for (int i=3; i>=0; i--) {
            int s = (res >> (i*8))&255; 
            smask.append(s);
            if (i > 0) {
                smask.append("."); 
            }
        }
    return smask.toString();
    }

    public static String SubnetMask(int cidr){
        int mask = 0xFFFFFFFF << (32 - cidr);
        StringBuilder  smask = new StringBuilder();
        for (int i=3; i>=0; i--) {
            int s = (mask >> (i*8))&255; 
            smask.append(s);
            if (i > 0) {
                smask.append("."); 
            }
        }
    return smask.toString();
    }
}