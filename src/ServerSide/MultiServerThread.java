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
    private String currentUserName;

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

    public void setCurrentUserName(String currentUserName){
        this.currentUserName = currentUserName;
    }

    public String getCurrentUserName(){
        return currentUserName;
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
        server.setUsersConnected(server.getUsersConnected() - 1);
        System.out.println("Users connected : " +  server.getUsersConnected());

        if (server.getUsersConnected() == 0) {
            System.out.println("closing server");
            server.getServerSocket().close();
            System.exit(0);
        }else {
            int i = 0;
            while (i < server.getUsernameList().size()) {
                if (server.getUsernameList().get(i).equals(currentUserName)) {
                    server.getUsernameList().remove(i);
                    server.getLastTextShown().remove(i);
                    server.getUserFrame().remove(i);

                    break;
                }
                i++;
            }
        }






    }


}