import java.io.*;
import java.net.*;

public class Server{
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println("Server is running...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected!");

            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            double num1 = Double.parseDouble(input.readUTF());
            double num2 = Double.parseDouble(input.readUTF());
            String operator = input.readUTF();
            double result = 0;
            String line = "";

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                default:
                    line = "Invalid Operator";
            }

            if(line.equals("Invalid Operator")){
                output.writeUTF(line);
            }
            else{
                output.writeUTF("Result: " + result);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
