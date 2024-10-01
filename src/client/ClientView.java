package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JTextField usernameField;
    private JTextField serverIpField;
    private JTextArea emailContentArea;
    private JButton sendEmailButton;
    private JButton connectButton;
    private JTextArea messageArea;
    private JPanel panel;

    public ClientView() {
        setTitle("Mail Client");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Server IP:"));
        serverIpField = new JTextField("127.0.0.1");
        topPanel.add(serverIpField);

        topPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        topPanel.add(usernameField);
        panel.add(topPanel, BorderLayout.NORTH);

        emailContentArea = new JTextArea(5, 30);
        panel.add(new JScrollPane(emailContentArea), BorderLayout.CENTER);

        sendEmailButton = new JButton("Send Email");
        connectButton = new JButton("Connect");
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(connectButton);
        bottomPanel.add(sendEmailButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);
        panel.add(new JScrollPane(messageArea), BorderLayout.EAST);

        add(panel);
        setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getServerIp() {
        return serverIpField.getText();
    }

    public String getEmailContent() {
        return emailContentArea.getText();
    }

    public void setMessage(String message) {
        messageArea.setText(message);
    }

    public void addSendEmailListener(ActionListener listener) {
        sendEmailButton.addActionListener(listener);
    }

    public void addConnectListener(ActionListener listener) {
        connectButton.addActionListener(listener);
    }
}
