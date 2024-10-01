package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(4445);
        System.out.println("Server is running...");

        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String request = new String(packet.getData(), 0, packet.getLength());
            String response = processRequest(request);

            byte[] responseBuf = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length, packet.getAddress(), packet.getPort());
            socket.send(responsePacket);
        }
    }

    // Xử lý các yêu cầu từ client
    private static String processRequest(String request) {
        String[] parts = request.split(":");
        String command = parts[0];

        if (command.equals("REGISTER")) {
            return registerUser(parts[1]);
        } else if (command.equals("LOGIN")) {
            return loginUser(parts[1]);
        } else if (command.equals("SEND_EMAIL")) {
            return sendEmail(parts[1], parts[2], parts[3]);
        }

        return "Unknown command!";
    }

    // Hàm đăng ký tài khoản
    private static String registerUser(String username) {
        File userDir = new File("accounts/" + username);
        if (userDir.exists()) {
            return "Account already exists.";
        }

        userDir.mkdirs();
        createWelcomeFile(userDir);
        return "Account registered successfully!";
    }

    // Hàm tạo file chào mừng sau khi đăng ký
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

    // Hàm đăng nhập và trả về danh sách file
    private static String loginUser(String username) {
        File userDir = new File("accounts/" + username);
        if (!userDir.exists()) {
            return "Account does not exist.";
        }

        String[] files = userDir.list();
        if (files == null || files.length == 0) {
            return "No files in account.";
        }

        return String.join(",", files);
    }

    // Hàm gửi email
    private static String sendEmail(String sender, String receiver, String emailContent) {
        File receiverDir = new File("accounts/" + receiver);
        if (!receiverDir.exists()) {
            return "Receiver does not exist.";
        }

        try {
            File emailFile = new File(receiverDir, "email_" + System.currentTimeMillis() + ".txt");
            FileWriter writer = new FileWriter(emailFile);
            writer.write("From: " + sender + "\n\n" + emailContent);
            writer.close();
            return "Email sent successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to send email!";
        }
    }
}
