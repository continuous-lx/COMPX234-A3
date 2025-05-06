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

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected successfully to server.");

            try(
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            ) {
                File file = new File(filename);
                Scanner scanner = new Scanner(file);
                
            }
            catch (IOException e){
                System.out.println("Error: " + e.getMessage());
            }

        }
        catch (IOException e){
                System.out.println("Error: " + e.getMessage());
            }
    }
}
