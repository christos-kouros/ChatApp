import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminMode extends JFrame implements ActionListener {
    private Server server;
    private MultiServerThread multiServerThread;
    private JButton historyButton;
    private Date date;
    private SimpleDateFormat formatter;


    public AdminMode(Server server, MultiServerThread multiServerThread) {
        this.server = server;
        this.multiServerThread = multiServerThread;

        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = new Date();

        server.getHistory().add("ADMIN connected     " + formatter.format(date));

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setTitle("You are connected as admin");
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));


        historyButton = new JButton();
        historyButton.setText("show History");
        historyButton.setMaximumSize(new Dimension(150, 50));
        historyButton.addActionListener(this);
        historyButton.setVisible(true);
        historyButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 25)));
        add(historyButton);


        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit ?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    dispose();

                    try {
                        date = new Date();
                        multiServerThread.exit("admin", date);
                    } catch (IOException e) {
                        System.out.println();
                    }

                }
            }
        });


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == historyButton) {
            System.out.println("History time");
            System.out.println(server.getHistory());
        }
    }
}
