import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public final class Server {
    private final ServerSocket serverSocket;
    private boolean listening = true;
    private int usersConnected = 0; // Users Connected
    private int activeUsers = 0; // Users active (users inside Chat page)
    private final ArrayList<String> usernameList; // Contains all the usernames currently connected
    private final ArrayList<String> textList; // Contains all the text messages that have been sended
    private final ArrayList<ChatPage> userFrame; // Contains all the user Frames
    private final ArrayList<Integer> lastTextShown; // For each users , shows the "id" of the last shown text

    public Server() throws IOException {
        serverSocket = new ServerSocket(8080);
        System.out.println("Server Started ");

        usernameList = new ArrayList<>();
        textList = new ArrayList<>();
        userFrame = new ArrayList<>();
        lastTextShown = new ArrayList<>();

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
        if (activeUsers < 0) {
            throw new IllegalArgumentException("Active Users should not be less than 0");
        }
        this.activeUsers = activeUsers;
    }

    public int getActiveUsers() {
        if (activeUsers < 0) {
            throw new IllegalArgumentException("Active Users should not be less than 0");
        }
        return activeUsers;
    }

    public void setUsersConnected(int usersConnected) {
        if (usersConnected < 0) {
            throw new IllegalArgumentException("Users connected should not be less than 0");
        }
        this.usersConnected = usersConnected;
    }

    public int getUsersConnected() {
        if (usersConnected < 0) {
            throw new IllegalArgumentException("Users connected should not be less than 0");
        }
        return usersConnected;
    }


    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            System.out.println("Could not call server !");
        }

    }
}