
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class ChatPage extends JFrame {
    private final Server server;
    private final MultiServerThread multiServerThread;
    private final JTextArea mainBody;
    private final JScrollPane mainBodyScrollPane;
    private final JTextField text;
    private final JLabel showUserCount;
    private final String username;
    private final SimpleDateFormat formatter;
    private Date date;

    public ChatPage(final Server server, final MultiServerThread multiServerThread, final String username) {
        this.server = server;
        this.multiServerThread = multiServerThread;
        this.username = username;

        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        server.getUsernameList().add(username);
        server.getUserFrame().add(this);
        server.setActiveUsers(server.getActiveUsers() + 1);
        server.getLastTextShown().add(0);


        server.getTextList().add(" - " + multiServerThread.getCurrentUserName() + " -  Connected   at  " + formatter.format(date = new Date()));
        server.getTextList().add("");

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setTitle("Connected   as  - " + multiServerThread.getCurrentUserName() + " -");
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


        mainBodyScrollPane = new JScrollPane(mainBody);
        mainBodyScrollPane.setMaximumSize(mainBodyScrollPane.getPreferredSize());
        mainBodyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        text = createJTextField();


        showUserCount = new JLabel("Active users :  " + server.getActiveUsers());
        showUserCount.setFont(showUserCount.getFont().deriveFont(20.0f));
        showUserCount.setAlignmentX(CENTER_ALIGNMENT);

        add(showUserCount);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(mainBodyScrollPane);
        add(Box.createRigidArea(new Dimension(0, 75)));
        add(text);

        refreshText();
        refreshUserCount();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {

                final int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit ?",
                        "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    try {
                        dispose();
                        multiServerThread.exit(date = new Date());
                        server.setActiveUsers(server.getActiveUsers() - 1);
                        refreshUserCount();
                        refreshText();
                    } catch (IOException e) {
                        System.out.println("");
                    }
                }
            }
        });


    }

    private JTextField createJTextField() {
        final JTextField text = new JTextField(45);
        text.setMaximumSize(text.getPreferredSize());
        text.setAlignmentX(CENTER_ALIGNMENT);
        text.setHorizontalAlignment(JTextField.CENTER);
        text.addActionListener(e -> {
            if (!text.getText().equals("")) {

                server.getTextList().add(text.getText());
                server.getTextList().add("- " + username + " -    " + formatter.format(date = new Date()));
                server.getTextList().add("");
                text.setText("");

                refreshText();
            }
        });
        text.setVisible(true);
        return text;
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
}