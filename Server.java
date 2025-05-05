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
                    while(!Thread.currentThread().isInterrupted()) {
                        displayServerSummary();
                        Thread.sleep(10000);
                    }
                }
                catch(Exception e) {
                    System.out.println("Error in timer thread: " + e.getMessage());
                }

            });

            timerThread.setDaemon(true);
            timerThread.start();

            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Socket threadClientSocket = clientSocket;

                }
                catch(IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        catch(IOException e) {
            System.out.println("Client connection error: " + e.getMessage());
        }
    }

    public static void displayServerSummary() {
        System.out.println("--- Display method called at: " + System.currentTimeMillis() + " ---");

        System.out.println("Current size: " + tupleSpace.getSize());
        System.out.println("Total READs : " + tupleSpace.getTotalReads());
        System.out.println("Total GETs: " + tupleSpace.getTotalGets());
        System.out.println("Total PUTs: " + tupleSpace.getTotalPut());
        System.out.println("Total Errors: " + tupleSpace.getTotalErrors());
        System.out.println("Average key size: " + tupleSpace.getAveKeySize());
        System.out.println("Average value size: " + tupleSpace.getAveValueSize()); 
        System.out.println("Average tuple size: " + tupleSpace.getAveTupleSize());
        System.out.println("--------------------------------------------------");
    }
}
