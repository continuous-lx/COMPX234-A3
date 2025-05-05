import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            Thread timerThread = new Thread(()-> {
                try {

                }
                catch(Exception e) {
                    System.out.println("Error in timer thread: " + e.getMessage());
                }

            });

            Socket clientSocket = serverSocket.accept();

        }
        catch(IOException e) {
            System.out.println("Client connection error: " + e.getMessage());
        }
    }
}
