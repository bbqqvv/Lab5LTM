package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JTextField usernameField;
    private JTextField receiverField;
    private JTextField serverIpField;
    private JTextArea emailContentArea;
    private JButton sendEmailButton;
    private JButton registerButton;
    private JButton loginButton;
    private JTextArea messageArea;
    private JPanel panel;
    private JList<String> fileList;

    public ClientView() {
        setTitle("Mail Client");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        topPanel.add(new JLabel("Server IP:"));
        serverIpField = new JTextField("127.0.0.1");
        topPanel.add(serverIpField);

        topPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        topPanel.add(usernameField);
        
        loginButton = new JButton("Login");
        topPanel.add(loginButton);

        registerButton = new JButton("Register");
        topPanel.add(registerButton);

        topPanel.add(new JLabel("Receiver:"));
        receiverField = new JTextField(20);
        topPanel.add(receiverField);

        panel.add(topPanel, BorderLayout.NORTH);

        emailContentArea = new JTextArea(5, 30);
        panel.add(new JScrollPane(emailContentArea), BorderLayout.CENTER);

        sendEmailButton = new JButton("Send Email");

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(sendEmailButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);
        panel.add(new JScrollPane(messageArea), BorderLayout.EAST);

        fileList = new JList<>();
        JScrollPane fileListScrollPane = new JScrollPane(fileList);
        fileListScrollPane.setPreferredSize(new Dimension(200, 100));
        panel.add(fileListScrollPane, BorderLayout.WEST);

        add(panel);
        setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getReceiver() {
        return receiverField.getText();
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

    public void updateFileList(String[] files) {
        fileList.setListData(files);
    }

    public void addSendEmailListener(ActionListener listener) {
        sendEmailButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}
