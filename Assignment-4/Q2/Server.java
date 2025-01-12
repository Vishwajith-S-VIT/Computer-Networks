import java.io.*;
import java.net.*;

public class Server{
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream In;
    private static DataOutputStream Out;
    public static void main(String [] args) throws IOException {
        serverSocket = new ServerSocket(5001);
        System.out.println("Server Listening on port 5001...");
        while (true) {
            socket = serverSocket.accept();
            In = new DataInputStream(socket.getInputStream());
            Out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    String n = In.readUTF();
                    String base = In.readUTF();

                    switch(base){
                        case "Binary":
                            Out.writeUTF(Integer.toString(Integer.parseInt(n, 10), 2));
                            break;
                        case "Octal":
                            Out.writeUTF(Integer.toString(Integer.parseInt(n, 10), 8));
                            break;
                        case "Hexadecimal":
                            Out.writeUTF(Integer.toString(Integer.parseInt(n, 10), 16));
                            break;
                    }
                    
                    socket.close();
                }
                catch(IOException e){
                    System.out.println(e);
                }
            }).start();
        }
    }
}