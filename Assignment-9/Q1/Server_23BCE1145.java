import java.util.*;
import java.net.*;
import java.io.*;

public class Server_23BCE1145{
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream din = null;
    private DataOutputStream dout = null;

    public Server_23BCE1145(int port){
        try{
            server = new ServerSocket(port);
            System.out.println("Server started\nWaiting for a client...");

            socket = server.accept();
            System.out.println("Client connected.\n");

            din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dout = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public void Work(){
        try{
            String line = din.readUTF();
            System.out.println("Received from client: " + line);

            String[] arr = line.split(" ");
            String ip = arr[0];
            int cidr = Integer.parseInt(arr[1]);
            int num = Integer.parseInt(arr[2]);

            int[] arrIps = new int[num];
            for (int i=0; i<num; i++) {
                arrIps[i] = Integer.parseInt(arr[3 + i]);
            }

            String line2 = calcSubnets(ip, cidr, arrIps);
            dout.writeUTF(line2);
        }
        catch(IOException i){
            System.out.println(i);
        }
        finally{
            System.out.println("\nClosing Connection with the client.");
            try{
                socket.close();
                 din.close();
                dout.close();
            }
            catch(IOException t){
                System.out.println(t);
            }
        }
    }

    public static String calcSubnets(String ip, int cidr, int[] arrIps){
        List<String> subnetInfo = new ArrayList<>();
        int cIP = ipToInt(ip);

        int n = arrIps.length;
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - i - 1; j++){
                if (arrIps[j] < arrIps[j + 1]){  
                    int temp = arrIps[j];
                    arrIps[j] = arrIps[j + 1];
                    arrIps[j + 1] = temp;
                }
            }
        }

        for (int i : arrIps){
            int p = (int)Math.pow(2,Math.ceil(Math.log(i)/Math.log(2)));
            int newcidr = 32 - (int) (Math.log(p)/Math.log(2));
            String mask = SubnetMask(newcidr);
            String faddr = intToIp(cIP);
            String laddr = intToIp(cIP+p-1);

            subnetInfo.add(
                "\nSubnet: " + faddr + "/" + newcidr +
                "\nMask: " + mask + 
                "\nRange: (" + faddr + ", " + laddr + ")" +
                "\nFirst address: " + faddr +
                "\nLast address: " + laddr
                );

            cIP += p;
        }

        return String.join("\n", subnetInfo);
    }

    public static int ipToInt(String ip){
        String[] arr = ip.split("\\.");
        int res = 0;
        for (int i=0; i<4; i++) { 
            res |= Integer.parseInt(arr[i]) << ((3-i)*8);
        }
        return res;
    }

    public static String intToIp(int ip){
        StringBuilder mask = new StringBuilder();
        for (int i=3; i>=0; i--) {
            int s = (ip >> (i*8))&255; 
            mask.append(s);
            if (i > 0) {
                mask.append("."); 
            }
        }
        return mask.toString();
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

    public static void main(String args[]){
        Server_23BCE1145 server = new Server_23BCE1145(5000);
        server.Work();
    }
}