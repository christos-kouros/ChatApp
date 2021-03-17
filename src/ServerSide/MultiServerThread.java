import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Αυτή η κλάση εξυπηρετεί όλες τις  λειτουργίες του Server !
 *
 * @author Christos_kouros_3440
 */
public class MultiServerThread extends Thread {


    private Socket clientSocket;
    private Server server;
    private String currentUserName;
    private SimpleDateFormat formatter;
    private Date date;

    /**
     * Constructor
     * <p>
     * Συνδέει τους πελάτες με τον Server
     */
    public MultiServerThread(Server Server, Socket clientSocket) {
        super("MailMultiServerThread");
        this.clientSocket = clientSocket;
        this.server = Server;
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        server.setUsersConnected(server.getUsersConnected() + 1);
        System.out.println("Users connected : " + server.getUsersConnected());

    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentUserName() {
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
    public void exit(String reason, Date date) throws IOException {
        if (reason.equals("admin")) {
            server.getHistory().add("ADMIN LEFT     " + formatter.format(date));
        } else {
            server.getHistory().add(currentUserName + "  LEFT  " + formatter.format(date));
        }

        clientSocket.close();

        server.setUsersConnected(server.getUsersConnected() - 1);

        System.out.println("Users connected : " + server.getUsersConnected());

        if (server.getUsersConnected() == 0) {
            System.out.println("closing server");
            server.getServerSocket().close();
            System.exit(0);
        } else {
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