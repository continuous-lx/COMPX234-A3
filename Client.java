import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("Error: invalid number of arguments");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];

        try (Socket socket = new Socket(hostname, port)) {
            
        }
        catch (IOException e){
                System.out.println("Error: " + e.getMessage());
            }
    }
}
