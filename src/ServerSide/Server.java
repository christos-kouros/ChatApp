import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    protected ServerSocket serverSocket = null;
    protected boolean listening = true;
    protected int usersConnected = 0;
    protected int activeUsers = 0;
    protected ArrayList<String> usernameList; // Contains all the usernames currently connected
    protected ArrayList<String> textList; // Contains all the text messages that have been sended
    protected ArrayList<ChatPage> userFrame; // Contains all the user Frames
    protected ArrayList<Integer> lastTextShown; // For each users , shows the "id" of the last shown text


    public Server() {

        /**
         * Ξεκίνημα του Server
         */
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server Started ");

            usernameList = new ArrayList<>();
            textList = new ArrayList<>();
            userFrame  = new ArrayList<>();
            lastTextShown = new ArrayList<>();

        }catch (Exception e) {
            System.out.println("Error trying to start server");
        }

        MultiServerThread MultiServerThread1;
        while (listening) {
            try {
                MultiServerThread1 = new MultiServerThread(this, serverSocket.accept());
                MultiServerThread1.start();
            } catch (Exception e) {
                listening = false;
            }

        }

    }









    public static void main(String[] args) throws Exception {
        try {
            Server mailServer = new Server();
        }catch (Exception e){
            System.out.println("Could not call server !");
        }

    }
}
