import javax.swing.*;
import java.io.IOException;
import java.net.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Server Status");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Server started");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(label);

            frame.setVisible(true);
        });

        // Specify the IP address directly
        InetAddress ipAddress;
        try {
            ipAddress = InetAddress.getByName("208.109.39.185");
        } catch (UnknownHostException e) {
            System.err.println("Failed to resolve IP address.");
            return;
        }

        // Start the server and bind to the IP address
        try (ServerSocket server = new ServerSocket(28040, 50, ipAddress)) {
            System.out.println("Server started!");
            new ListChecker().start();
            System.out.println("Pinging server list started");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client Connected");
                new Connection(socket).start();
            }
        } catch (IOException ex) {
            System.err.println("Error starting the server: " + ex.getMessage());
        }
    }
}
