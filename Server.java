import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    static TupleSpace tupleSpace = new TupleSpace();

    public static void main(String[] args) {
        if (args.length != 1) { 
            System.out.println("Error: invalid argument."); 
            return; 
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } 
        catch(NumberFormatException e) {
            System.out.println("Error: invalid port number. Please enter a valid integer");
            return;
        }

        try(ServerSocket serverSocket = new ServerSocket(port)) {

        }
        catch(IOException e) {
            System.out.println("Client connection error: " + e.getMessage());
        }
    }
}
