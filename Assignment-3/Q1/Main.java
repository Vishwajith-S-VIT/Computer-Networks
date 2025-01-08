import java.net.*;

public class Main {
    public static void main(String[] args){
        try{
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("IP Address: " + inetAddress.getHostAddress());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
    }
}
