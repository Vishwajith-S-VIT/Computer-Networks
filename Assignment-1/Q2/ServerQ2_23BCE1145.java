import java.io.*;
import java.net.*;

public class ServerQ2_23BCE1145 {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;

    public ServerQ2_23BCE1145(int port){
        try{
            System.out.println("Starting the server.\n");
            server = new ServerSocket(port);
            System.out.println("Waiting for a client request.\n");
            socket = server.accept();
            System.out.println("Connected to a client.\n");
            input = new DataInputStream(socket.getInputStream());
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public void Check(Integer n){
        int i;
        for(i = 2; i < n; i++){
            if(n % i == 0){
                System.out.println(n + " is not a prime number.");
                return;
            }
        }
        if(n == 1){
            System.out.println("1 is neither prime nor composite.");
        }
        else if(n <= 0){
            System.out.println(n + " is not a natural number.");
        }
        else{
            System.out.println(n + " is a prime number.");
        }
    }

    public void Work(){
        String line = "";
            while(!line.equalsIgnoreCase("end")){
                try{
                    line = input.readUTF();
                    Check(Integer.parseInt(line));
                }
                catch(IOException i){
                    System.out.println(i);
                }
                catch(NumberFormatException n){
                    if(line.equalsIgnoreCase("End")){
                        break;
                    }
                    System.out.println("Input a number, and nothing else.");
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
        ServerQ2_23BCE1145 server = new ServerQ2_23BCE1145(5000);
        server.Work();
    }
}
