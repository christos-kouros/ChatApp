import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Αυτή η κλάση εξυπηρετεί όλες τις  λειτουργίες του Server !
 *
 * @author Christos_kouros_3440
 */
public class MultiServerThread extends Thread {

    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;
    private String inputLine;
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
        System.out.println("Users connected : " + (++Server.usersConnected));
    }

    public void run() {
        try {

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            /**
             * Οσο ο πελάτης Δεν στέλνει το μήνυμα "exit" :
             */
            while (!(inputLine = in.readLine()).equals("exit")) {

                if (inputLine.equals("Connect")) {
                    System.out.println("Connecting user");
                    LoginPage mainPage = new LoginPage(server,this);

                }else {
                    System.out.println("Trying");
                }

            }
            /**
             * Εξοδος
             */
            exit();


        } catch (IOException e) {
            System.out.println();
        }
    }



    /**
     * Η λειτουργία αυτή τερματίζει την σύνδεση του πελάτη με τον
     * εξυπηρετητή και θα απελευθερώνει τους πόρους του συστήματος.
     *
     * @throws IOException
     */
    public void exit() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
        System.out.println("Users connected : "+ --server.usersConnected);
        server.usernameList.remove(currentUserName);


        if (server.usersConnected == 0) {
            System.out.println("closing server");
            server.serverSocket.close();
            System.exit(0);
        }

    }



}