import java.io.*;
import java.net.*;

public class ServerQ3_23BCE1145 {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public ServerQ3_23BCE1145(int port){
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

    public void Check(Integer n){
        int i;
        try{
            for(i = 2; i < n; i++){
                if(n % i == 0){
                    output.writeUTF(n + " is not a prime number.");
                    return;
                }
            }
            if(n == 1){
                output.writeUTF("1 is neither prime nor composite.");
            }
            else if(n <= 0){
                output.writeUTF(n + " is not a natural number.");
            }
            else{
                output.writeUTF(n + " is a prime number.");
            }
        }
        catch(IOException i1){
            System.out.println(i1);
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
        ServerQ3_23BCE1145 server = new ServerQ3_23BCE1145(5000);
        server.Work();
    }
}
