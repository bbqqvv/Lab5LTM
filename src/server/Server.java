package server;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Server {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(4445);

        System.out.println("Server is running...");

        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            String response = processRequest(received);

            byte[] responseBuf = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length, packet.getAddress(), packet.getPort());
            socket.send(responsePacket);
        }
    }

    private static String processRequest(String request) {
        String[] parts = request.split(":");
        String command = parts[0];

        if (command.equals("SEND_EMAIL")) {
            String username = parts[1];
            String emailContent = parts[2];

            // Tạo thư mục account nếu chưa tồn tại
            File userDir = new File("accounts/" + username);
            if (!userDir.exists()) {
                userDir.mkdirs();
                createWelcomeFile(userDir);
            }

            // Lưu email
            try {
                File emailFile = new File(userDir, "email_" + System.currentTimeMillis() + ".txt");
                FileWriter writer = new FileWriter(emailFile);
                writer.write(emailContent);
                writer.close();
                return "Email sent successfully!";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to send email!";
            }
        }

        return "Unknown command!";
    }

    private static void createWelcomeFile(File userDir) {
        try {
            File welcomeFile = new File(userDir, "new_email.txt");
            FileWriter writer = new FileWriter(welcomeFile);
            writer.write("Thank you for using this service. We hope that you will feel comfortable...");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
