package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    private ClientView view;
    private Client client;

    public ClientController(ClientView view) {
        this.view = view;

        // Sự kiện đăng ký tài khoản
        view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String serverIp = view.getServerIp();
                    client = new Client(serverIp, 4445);

                    String username = view.getUsername();
                    String response = client.sendRequest("REGISTER:" + username);
                    view.setMessage(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.setMessage("Register failed!");
                }
            }
        });

        // Sự kiện đăng nhập
        view.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (client == null) {
                        view.setMessage("Please register first.");
                        return;
                    }

                    String username = view.getUsername();
                    String response = client.sendRequest("LOGIN:" + username);
                    
                    if (response.startsWith("No files")) {
                        view.setMessage("Login successful. No files.");
                    } else {
                        String[] files = response.split(",");
                        view.updateFileList(files);
                        view.setMessage("Login successful. Files received.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.setMessage("Login failed!");
                }
            }
        });

        // Sự kiện gửi email
        view.addSendEmailListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (client == null) {
                        view.setMessage("Please register first.");
                        return;
                    }

                    String username = view.getUsername();
                    String receiver = view.getReceiver();
                    String emailContent = view.getEmailContent();
                    String response = client.sendRequest("SEND_EMAIL:" + username + ":" + receiver + ":" + emailContent);
                    view.setMessage(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.setMessage("Failed to send email!");
                }
            }
        });
    }
}
