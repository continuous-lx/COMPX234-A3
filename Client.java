public class Client {
    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("Error: invalid number of arguments");
            return;
        }
        
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];
    }
}
