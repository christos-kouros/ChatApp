import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatPage extends JFrame implements ActionListener {
    private Server server;
    private MultiServerThread multiServerThread;
    private JTextArea mainBody;
    private JScrollPane mainBodyScollPane;
    private JTextField text;
    private JLabel showUserCount;
    private String username;


    public ChatPage(Server server, MultiServerThread multiServerThread , String username) {
        this.server = server;
        this.multiServerThread = multiServerThread;
        this.username = username;

        server.usernameList.add(username);
        server.userData.add(multiServerThread);
        server.userFrame.add(this);
        server.activeUsers++;


        setSize(600, 600);
        setResizable(false);
        setTitle("Chat Page");
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


        showUserCount = new JLabel("Active users :  "+ server.activeUsers);
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
                        server.activeUsers--;
                        refreshUserCount();
                    } catch (IOException e) {
                        System.out.println("");
                    }
                }
            }
        });

        setVisible(true);

    }

    public void refreshUserCount(){
        int i = 0;
        while (i < server.userFrame.size()) {
            if(server.activeUsers > 1){
                server.userFrame.get(i).showUserCount.setForeground(Color.GREEN);
            }else {
                server.userFrame.get(i).showUserCount.setForeground(Color.RED);
            }
            server.userFrame.get(i).showUserCount.setText("Active users :  "+ server.activeUsers);
            i++;

        }

    }

    public void refreshText() {

        int i, j;

        i = 0;
        while (i < server.userFrame.size()) {

            j = 0;
            server.userFrame.get(i).mainBody.setText(null);
            while (j < server.textList.size()) {
                server.userFrame.get(i).mainBody.append(server.textList.get(j) + '\n');
                j++;
            }

            i++;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == text) {
            server.textList.add(text.getText());
            server.textList.add("- "+username+" -");
            text.setText("");

            refreshText();
        }
    }
}
