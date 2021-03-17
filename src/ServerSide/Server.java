import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket = null;
    private boolean listening = true;
    private int usersConnected = 0; // Users Connected
    private int activeUsers = 0; // Users active (users inside Chat page)
    private ArrayList<String> usernameList; // Contains all the usernames currently connected
    private ArrayList<String> textList; // Contains all the text messages that have been sended
    private ArrayList<ChatPage> userFrame; // Contains all the user Frames
    private ArrayList<Integer> lastTextShown; // For each users , shows the "id" of the last shown text


    public Server() {

        /**
         * Ξεκίνημα του Server
         */
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server Started ");

            usernameList = new ArrayList<>();
            textList = new ArrayList<>();
            userFrame = new ArrayList<>();
            lastTextShown = new ArrayList<>();

        } catch (Exception e) {
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

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ArrayList<Integer> getLastTextShown() {
        return lastTextShown;
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }

    public ArrayList<String> getTextList() {
        return textList;
    }

    public ArrayList<ChatPage> getUserFrame() {
        return userFrame;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setUsersConnected(int usersConnected) {
        this.usersConnected = usersConnected;
    }

    public int getUsersConnected() {
        return usersConnected;
    }


    public static void main(String[] args) throws Exception {
        try {
            Server mailServer = new Server();
        } catch (Exception e) {
            System.out.println("Could not call server !");
        }

    }
}
