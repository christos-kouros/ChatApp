
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Date;

public final class LoginPage extends JFrame implements ActionListener, KeyListener {

    private final JLabel menulabel;
    private final JTextField user;
    private final JButton confirm;
    private final Server server;
    private final MultiServerThread multiServerThread;
    private Date date;

    public LoginPage(final Server server, final MultiServerThread multiServerThread) {
        this.multiServerThread = multiServerThread;
        this.server = server;


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
                    } catch (IOException e) {
                        System.out.println("");
                    }
                }
            }
        });


    }

    private boolean check(String name) {
        return !server.getUsernameList().contains(name);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == confirm) {

            final String name = user.getText();

            if (check(user.getText())) {
                multiServerThread.setCurrentUserName(name);
                dispose();
                multiServerThread.setCurrentUserName(name);
                new ChatPage(server, multiServerThread, name);

            } else {
                System.out.println("Username invalid");
                user.setText("");
            }

        }

    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            confirm.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}