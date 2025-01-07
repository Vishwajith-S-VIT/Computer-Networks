import java.io.*;
import java.net.*;

public class ServerQ1_23BCE1145 {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public ServerQ1_23BCE1145(int port){
        try{
            System.out.println("Starting the server.\n");
            server = new ServerSocket(port);
            System.out.println("Waiting for a client request.\n");
            socket = server.accept();
            System.out.println("Connected to a client.\n");
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public int power(int x, long y)
    {
        if (y == 0)
            return 1;
        if (y % 2 == 0)
            return power(x, y / 2) * power(x, y / 2);
        return x * power(x, y / 2) * power(x, y / 2);
    }

    public int order(int x)
    {
        int n = 0;
        while (x != 0) {
            n++;
            x = x / 10;
        }
        return n;
    }

    public boolean isArmstrong(int x)
    {
        int n = order(x);
        int temp = x, sum = 0;
        while (temp != 0) {
            int r = temp % 10;
            sum = sum + power(r, n);
            temp = temp / 10;
        }

        return (sum == x);
    }
    public void Check(Integer n){
        try{
            if(isArmstrong(n)){
                output.writeUTF(n + " is an Armstrong number.");
            }
            else{
                output.writeUTF(n + " is not an Armstrong number.");
            }
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public void Work(){
        String line = "";
            while(!line.equalsIgnoreCase("end")){
                try{
                    line = input.readUTF();
                    Check(Integer.parseInt(line));
                    System.out.println(line);
                }
                catch(IOException i){
                    System.out.println(i);
                }
                catch(NumberFormatException n){
                    if(line.equalsIgnoreCase("End")){
                        break;
                    }
                    try{
                        System.out.println(line);
                        output.writeUTF("Input a number, and nothing else.");
                    }
                    catch(IOException i1){
                        System.out.println(i1);
                    }
                }
            }

        try{
            System.out.println("\nClosing the server.");

            input.close();
            socket.close();
            server.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public static void main(String[] args){
        ServerQ1_23BCE1145 server = new ServerQ1_23BCE1145(5000);
        server.Work();
    }
}
