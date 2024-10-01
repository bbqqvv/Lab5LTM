package client;

public class ClientMain {
    public static void main(String[] args) {
        // Tạo giao diện và controller
        ClientView view = new ClientView();
        ClientController controller = new ClientController(view);
    }
}
