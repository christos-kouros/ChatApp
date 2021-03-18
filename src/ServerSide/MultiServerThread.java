import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiServerThread extends Thread {


    private Socket clientSocket;
    private Server server;
    private String currentUserName;
    private SimpleDateFormat formatter;

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

    public void exit(Date date) throws IOException {
        clientSocket.close();

        server.getTextList().add(" - " + currentUserName + " -    Left  " + formatter.format(date));
        server.getTextList().add("");
        server.setUsersConnected(server.getUsersConnected() - 1);

        System.out.println("Users connected : " + server.getUsersConnected());

        if (server.getUsersConnected() == 0) {
            System.out.println("closing server");
            server.getServerSocket().close();
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