import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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

            
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            Thread timerThread = new Thread(()-> {
                try {
                    while(!Thread.currentThread().isInterrupted()) {
                        displayServerSummary();
                        Thread.sleep(10000);
                    }
                } catch(InterruptedException e) {
                    System.out.println("Error in timer thread: " + e.getMessage());
                } catch(Exception e) {
                    System.out.println("Error in timer thread: " + e.getMessage());
                }

            });

            timerThread.setDaemon(true);
            timerThread.start();

            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Socket threadClientSocket = clientSocket;
                    Thread newClient = new Thread(() -> {
                        try {

                            System.out.println("Connection establish with " + threadClientSocket.getInetAddress());

                        	InputStream input = threadClientSocket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                            OutputStream output = threadClientSocket.getOutputStream();
                            PrintWriter writer = new PrintWriter(output, true);
                            
                            while(true) {
                                String request = reader.readLine();
                                if (request == null) {
                                    System.out.println("Client disconnected");
                                    break;
                                }
                                writer.println(processInput(request));
                            }

                        }
                        catch(Exception e){
                            System.out.println("Client handling error: " + e.getMessage());
                        }
                        finally{
                            try{
                                threadClientSocket.close();
                            }
                            catch(Exception e){
                                System.out.println("Failed to close client: " + e.getMessage());
                            }
                        }
                    });
                    newClient.start();
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

    public static String processInput(String request) {
        String output = "";

        try {
            String[] requestArray = request.split(" ", 4);
            String messageLength = requestArray[0];
            String command = requestArray[1];
            String key = requestArray[2];
            String value = "";

            if (requestArray.length > 3){
                value = requestArray[3];
            }

            output += messageLength + " ";

            if (command.equals("R")){
                value = tupleSpace.read(key);
                if (value != ""){
                    output += "OK (" + key + ", " + value + ") read"; 
                }
                else{
                    output += "ERR (" + key + ") does not exist";
                }
            }
            else if(command.equals("G")) {
                value = tupleSpace.get(key);
                if (value != ""){
                    output += "OK (" + key + ", " + value + ") removed"; 
                }
                else{
                    output += "ERR (" + key + ") does not exist";
                }
            }
            else if (command.equals("P")) {
                int e = tupleSpace.put(key, value);
                if (e == 1){
                    output += "OK (" + key + ", " + value + ") added"; 
                }
                else{
                    output += "ERR (" + key + ") already exist";
                }
            }
            else {
                System.out.println("Error: invalid action");
                return output;
            }
            return output;
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            return output;
        }
    }
}
