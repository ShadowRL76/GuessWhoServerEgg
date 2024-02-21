import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class App {

    public static void main(String[] args) {
        final String[] ip = new String[1];
        new Thread (() -> {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            InetAddress addr = addresses.nextElement();
                            if (addr.getAddress().length == 4) { // Check for IPv4 addresses
                                ip[0] = addr.getHostAddress();
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }

            System.out.println(ip[0]);

            try (ServerSocket server = new ServerSocket(28040)) {
                System.out.println("Server started!");
                new ListChecker().start();
                System.out.println("Pinging server list started");
                while (true) {
                    Socket socket = server.accept();
                    System.out.println("Client Connected");
                    new Connection(socket).start();
                }
            } catch (IOException ex) {
            }

        }).start();
    }
}
