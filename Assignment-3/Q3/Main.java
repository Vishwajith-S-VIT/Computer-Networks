import java.net.*;

public class Main {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        try{
            URL url = new URL("https://vishwajith-s-vit.github.io/");
            InetAddress ip = InetAddress.getByName(url.getHost());
            System.out.println("IP Address: " + ip.getHostAddress());
        }
        catch(MalformedURLException m){
            System.out.println(m);
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
    }
}
