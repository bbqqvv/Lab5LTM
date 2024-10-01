package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    private ClientView view;
    private Client client;

    public ClientController(ClientView view) {
        this.view = view;

        view.addConnectListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String serverIp = view.getServerIp();
                    client = new Client(serverIp, 4445); // Cổng động của server
                    view.setMessage("Connected to server: " + serverIp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.setMessage("Connection failed!");
                }
            }
        });

        view.addSendEmailListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (client == null) {
                        view.setMessage("Please connect to the server first.");
                        return;
                    }

                    String username = view.getUsername();
                    String emailContent = view.getEmailContent();
                    String response = client.sendRequest("SEND_EMAIL:" + username + ":" + emailContent);
                    view.setMessage(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.setMessage("Failed to send email!");
                }
            }
        });
    }
}
