import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = null;


        try {
            socket = new Socket("127.0.0.1", 8080);
        } catch (Exception e) {
            System.err.println("Error occurred ... Closing program");
            System.exit(-1);
        }


    }
}
