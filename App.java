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

        // Resolve the DDNS hostname to an IP address
        InetAddress ddnsAddress;
        try {
            ddnsAddress = InetAddress.getByName("gameservers.wolfhunter1043.com");
        } catch (UnknownHostException e) {
            System.err.println("Failed to resolve DDNS hostname.");
            return;
        }

        // Start the server and bind to the DDNS address
        try (ServerSocket server = new ServerSocket(28040, 50, ddnsAddress)) {
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
