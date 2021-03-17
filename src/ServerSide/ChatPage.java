import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatPage extends JFrame implements ActionListener {
    private Server server;
    private MultiServerThread multiServerThread;
    private JTextArea mainBody;
    private JScrollPane mainBodyScollPane;
    private JTextField text;
    private JLabel showUserCount;
    private String username;
    private SimpleDateFormat formatter;
    private Date date;

    public ChatPage(Server server, MultiServerThread multiServerThread, String username) {
        this.server = server;
        this.multiServerThread = multiServerThread;
        this.username = username;

        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        server.getUsernameList().add(username);
        server.getUserFrame().add(this);
        server.setActiveUsers(server.getActiveUsers() + 1);
        server.getLastTextShown().add(0);


        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setTitle("Connected as : " + multiServerThread.getCurrentUserName());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        mainBody = new JTextArea(15, 30);
        mainBody.setMaximumSize(mainBody.getPreferredSize());
        mainBody.setLineWrap(true);
        mainBody.setWrapStyleWord(true);
        mainBody.setBorder(BorderFactory.createBevelBorder(1));
        mainBody.setFont(new Font("Comic Sans", Font.ITALIC, 15));
        mainBody.setEditable(false);
        mainBody.setVisible(true);


        mainBodyScollPane = new JScrollPane(mainBody);
        mainBodyScollPane.setMaximumSize(mainBodyScollPane.getPreferredSize());
        mainBodyScollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        text = new JTextField(45);
        text.setMaximumSize(text.getPreferredSize());
        text.setAlignmentX(CENTER_ALIGNMENT);
        text.setHorizontalAlignment(JTextField.CENTER);
        text.addActionListener(this);
        text.setVisible(true);


        showUserCount = new JLabel("Active users :  " + server.getActiveUsers());
        showUserCount.setFont(showUserCount.getFont().deriveFont(20.0f));
        showUserCount.setAlignmentX(CENTER_ALIGNMENT);

        add(showUserCount);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(mainBodyScollPane);
        add(Box.createRigidArea(new Dimension(0, 75)));
        add(text);

        refreshText();
        refreshUserCount();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit ?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        dispose();
                        multiServerThread.exit();
                        server.setActiveUsers(server.getActiveUsers() - 1);
                        refreshUserCount();
                    } catch (IOException e) {
                        System.out.println("");
                    }
                }
            }
        });

        setVisible(true);

    }

    public void refreshUserCount() {
        int i = 0;
        while (i < server.getUserFrame().size()) {
            if (server.getActiveUsers() > 1) {
                server.getUserFrame().get(i).showUserCount.setForeground(Color.GREEN);
            } else {
                server.getUserFrame().get(i).showUserCount.setForeground(Color.RED);
            }
            server.getUserFrame().get(i).showUserCount.setText("Active users :  " + server.getActiveUsers());
            i++;

        }

    }

    public void refreshText() {

        int i, j;

        i = 0;
        while (i < server.getUserFrame().size()) {
            j = server.getLastTextShown().get(i);

            while (j < server.getTextList().size()) {
                server.getUserFrame().get(i).mainBody.append(server.getTextList().get(j) + '\n');
                j++;
            }
            server.getLastTextShown().set(i, j);

            i++;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == text && !text.getText().equals("")) {


            server.getTextList().add(text.getText());
            date = new Date();
            server.getTextList().add("- " + username + " -    " + formatter.format(date));
            server.getTextList().add("");
            text.setText("");

            refreshText();
        }
    }
}
