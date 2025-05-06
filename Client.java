import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("Error: invalid number of arguments");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];

    try (Socket socket = new Socket(hostname, port);
         OutputStream output = socket.getOutputStream();
         PrintWriter writer = new PrintWriter(output, true);
         InputStream input = socket.getInputStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(input));) {
        System.out.println("Connected successfully to server.");

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()){
                continue;
            }

            String[] requestArray = line.split(" ", 3);
            
            if (requestArray.length < 2) {
                System.out.println("Error: Invalid command format - " + line);
                continue;
            }

            String command = requestArray[0];
            String key = requestArray[1];
            String value = "";
            
            if (requestArray.length > 2){
                value = requestArray[2];
            }

            String protocolLine = "";

            int messageLength = 6 + key.length() + value.length();

            if (messageLength >= 7 && messageLength <= 999){
                if(messageLength < 10){
                    protocolLine += "00";
                }
                else if (messageLength < 100){
                    protocolLine += "0";
                }
                protocolLine += messageLength + " ";
            }
            else{
                System.out.println("Error: invalid request length");
                continue;
            }

            if (command.equals("READ")){
                protocolLine += "R ";
            }
            else if (command.equals("GET")){
                protocolLine += "G ";
            }
            else if (command.equals("PUT")){
                protocolLine += "P ";
            }
            else{
                System.out.println("Error: invalid command");
                continue;
            }

            protocolLine += key + " " + value;

            writer.println(protocolLine);

            String response = reader.readLine();

            if (response == null) {
                System.out.println("Error: Server closed connection");
                break;
            }

            String[] responseArray = response.split(" ", 2);
            String serverResponse;
            if (responseArray.length > 1){
                serverResponse = responseArray[1];
            }
            else{
                serverResponse = responseArray[0];
            }

            String clientOutput = command + " " + key + " " + value + ": " + serverResponse;
            System.out.println(clientOutput);
        }
        scanner.close();    
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
