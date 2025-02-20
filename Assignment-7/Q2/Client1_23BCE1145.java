import java.io.*;
import java.net.*;
import java.util.*;

public class Client1_23BCE1145 {
    private Socket socket = null;
    private ObjectOutputStream output = null;
    private DataInputStream input = null;

    public Client1_23BCE1145(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Client is connected!\n");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
        } 
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public void Work() {
        try {
            File file = new File("file1.txt");
            Set<String> words = getWordsFromFile(file);
            output.writeObject(words);
            output.flush();

            String similarity = input.readUTF();
            System.out.println("Received Similarity: " + similarity + "%\n");
        } 
        catch (IOException i) {
            System.out.println(i);
        }
        closeConnection();
    }

    private Set<String> getWordsFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Set<String> words = new HashSet<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.toLowerCase().split("\\W+");
            words.addAll(Arrays.asList(tokens));
        }
        reader.close();
        return words;
    }

    private void closeConnection() {
        try {
            System.out.println("Closing connection.");
            output.close();
            input.close();
            socket.close();
        } 
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Client1_23BCE1145 client = new Client1_23BCE1145("127.0.0.1", 5000);
        client.Work();
    }
}
