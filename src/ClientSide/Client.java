import java.io.IOException;
import java.net.Socket;

public final class Client {

    public static void main(String[] args) {

        try {
            new Socket("127.0.0.1", 8080);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error occurred ... Closing program");
            System.exit(-1);
        }
    }
}