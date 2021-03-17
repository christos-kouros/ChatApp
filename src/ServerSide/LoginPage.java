import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginPage extends JFrame implements ActionListener , KeyListener {

    private JLabel menulabel;
    private JTextField user;
    private JButton confirm;
    private Server server;
    private MultiServerThread multiServerThread;

    public LoginPage(Server server, MultiServerThread multiServerThread) {
        this.multiServerThread = multiServerThread;
        this.server = server;


        System.out.println("Users connected : " + ++server.usersConnected);

        setSize(600, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Login Page");
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        menulabel = new JLabel("Hello, pls add a username");
        menulabel.setFont(menulabel.getFont().deriveFont(20.0f));
        menulabel.setAlignmentX(CENTER_ALIGNMENT);


        user = new JTextField(20);
        user.addActionListener(this);
        user.addKeyListener(this);
        user.setMaximumSize(user.getPreferredSize());
        user.setAlignmentX(CENTER_ALIGNMENT);
        user.setHorizontalAlignment(JTextField.CENTER);
        user.setVisible(true);


        confirm = new JButton();
        confirm.setText("Connect");
        confirm.setMaximumSize(new Dimension(100, 50));
        confirm.addActionListener(this);
        confirm.setVisible(true);
        confirm.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 25)));
        add(menulabel);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(user);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(confirm);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit ?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        dispose();
                        multiServerThread.exit();
                    } catch (IOException e) {
                        System.out.println("");
                    }
                }
            }
        });


        setVisible(true);


    }

    public boolean check(String name) {
        if (!server.usernameList.contains(name)) {
            multiServerThread.currentUserName = name;
            return true;
        } else {
            return false;
        }

    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == confirm) {

            if (check(user.getText())) {
                multiServerThread.currentUserName = user.getText();
                dispose();
                ChatPage chatPage = new ChatPage(server, multiServerThread , user.getText());
            } else {
                System.out.println("Username invalid");
                user.setText("");
            }

        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            confirm.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
