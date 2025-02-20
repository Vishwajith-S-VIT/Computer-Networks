import java.io.*;
import java.net.*;
import java.util.*;

public class Server_23BCE1145 {
    private ServerSocket server = null;
    private Socket socket1 = null, socket2 = null, socket3 = null;
    private ObjectInputStream input1 = null, input2 = null, input3 = null;
    private DataOutputStream output1 = null, output2 = null, output3 = null;

    public Server_23BCE1145(int port) {
        try {
            System.out.println("\nStarting server.");
            server = new ServerSocket(port);
            System.out.println("Waiting for 3 clients...\n");

            socket1 = server.accept();
            System.out.println("Client 1 connected.");
            socket2 = server.accept();
            System.out.println("Client 2 connected.");
            socket3 = server.accept();
            System.out.println("Client 3 connected.\n");

            input1 = new ObjectInputStream(socket1.getInputStream());
            input2 = new ObjectInputStream(socket2.getInputStream());
            input3 = new ObjectInputStream(socket3.getInputStream());

            output1 = new DataOutputStream(socket1.getOutputStream());
            output2 = new DataOutputStream(socket2.getOutputStream());
            output3 = new DataOutputStream(socket3.getOutputStream());
        } 
        catch (IOException i) {
            System.out.println(i);
        }
    }

    @SuppressWarnings("unchecked")
    public void Work() {
        try {
            Set<String> words1 = (Set<String>) input1.readObject();
            Set<String> words2 = (Set<String>) input2.readObject();
            Set<String> words3 = (Set<String>) input3.readObject();

            double sim12 = calculateJaccardSimilarity(words1, words2);
            double sim13 = calculateJaccardSimilarity(words1, words3);
            double sim23 = calculateJaccardSimilarity(words2, words3);

            double avgSimilarity = (sim12 + sim13 + sim23) / 3.0 * 100;

            sendResult(output1, avgSimilarity);
            sendResult(output2, avgSimilarity);
            sendResult(output3, avgSimilarity);

            System.out.println("Similarity sent to all clients.\n");
        } 
        catch (IOException | ClassNotFoundException i) {
            System.out.println(i);
        }
        closeConnections();
    }

    private double calculateJaccardSimilarity(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    private void sendResult(DataOutputStream output, double similarity) throws IOException {
        output.writeUTF(String.format("%.2f", similarity));
        output.flush();
    }

    private void closeConnections() {
        try {
            System.out.println("Closing connections.");
            input1.close();
            input2.close();
            input3.close();
            output1.close();
            output2.close();
            output3.close();
            socket1.close();
            socket2.close();
            socket3.close();
            server.close();
        } 
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Server_23BCE1145 server = new Server_23BCE1145(5000);
        server.Work();
    }
}
