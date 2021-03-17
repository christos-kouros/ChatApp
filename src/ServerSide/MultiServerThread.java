import java.io.IOException;
import java.net.Socket;

/**
 * Αυτή η κλάση εξυπηρετεί όλες τις  λειτουργίες του Server !
 *
 * @author Christos_kouros_3440
 */
public class MultiServerThread extends Thread {


    private Socket clientSocket;
    private Server server;
    protected String currentUserName;

    /**
     * Constructor
     * <p>
     * Συνδέει τους πελάτες με τον Server
     */
    public MultiServerThread(Server Server, Socket clientSocket) {
        super("MailMultiServerThread");
        this.clientSocket = clientSocket;
        this.server = Server;
    }

    public void run() {

        LoginPage mainPage = new LoginPage(server, this);

    }


    /**
     * Η λειτουργία αυτή τερματίζει την σύνδεση του πελάτη με τον
     * εξυπηρετητή και θα απελευθερώνει τους πόρους του συστήματος.
     *
     * @throws IOException
     */
    public void exit() throws IOException {
        clientSocket.close();
        System.out.println("Users connected : " + --server.usersConnected);

        if (server.usersConnected == 0) {
            System.out.println("closing server");
            server.serverSocket.close();
            System.exit(0);
        }else {
            int i = 0;
            while (i < server.usernameList.size()) {
                if (server.usernameList.get(i).equals(currentUserName)) {
                    server.usernameList.remove(i);
                    server.lastTextShown.remove(i);
                    server.userFrame.remove(i);

                    break;
                }
                i++;
            }
        }






    }


}