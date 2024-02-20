import java.net.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Connection extends Thread {

  private Socket socket;

  public Connection(Socket client) {
    this.socket = client;
  }

  @Override
  public void run() {
    try(
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream())
      ) {
      while (true) {
        String command = input.readLine();
        if (command != null) {

          // Client Commands
          if (command.substring(0, 3).equals("CLI")) {

            // Command: Get Server List
            if (command.substring(3, 6).equals("GSL")) {
              output.write(("SLL"+Storage.getServerList().size()+"\r\n").getBytes());
              synchronized(Storage.getServerList()) {
                Iterator<Server> it = Storage.getServerList().iterator();
                while (it.hasNext()) {
                  Server e = it.next();
                  output.write((e.getName()+"\r\n").getBytes());
                }
              }
                output.flush();

            // Command: Server Join
            } else if (command.substring(3, 6).equals("SJN")) {
              synchronized(Storage.getServerList()) {
                String serverName = command.substring(6);
                Iterator<Server> it = Storage.getServerList().iterator();
                while (it.hasNext()) {
                  Server e = it.next();
                  if (e.getName().equals(serverName)) {
                    output.write((e.getIP() + "," + e.getPort() + "\r\n").getBytes());
                    break;
                  }
                }
              }
              output.flush();
            }

          // Server Commands
          } else if (command.substring(0, 3).equals("SER")) {

            // Command: Server Add
            if (command.substring(3, 6).equals("ADD")) {
              String serverName = input.readLine();
              String ip = input.readLine();
              String port = input.readLine();
              
              synchronized(Storage.getServerList()) {
                Iterator<Server> it = Storage.getServerList().iterator();
                boolean exists = false;
                while (it.hasNext()) {
                  Server e = it.next();
                  if (e.getName().equals(serverName)) {
                    exists = true;
                    output.write(("ERN" + "\r\n").getBytes());
                    break;
                  }
                }
                if (!exists) {
                  ArrayList<Server> serverList = Storage.getServerList();
                  serverList.add(new Server(serverName, ip, Integer.parseInt(port)));
                  Storage.setServerList(serverList);
                  output.write(("FIN"+"\r\n").getBytes());
                }
              }
              output.flush();
            
            // Command: Server Delete
            } else if (command.substring(3, 6).equals("DEL")) {
              synchronized(Storage.getServerList()) {
                String serverName = command.substring(6);
                Iterator<Server> itr = Storage.getServerList().iterator();
    
                while (itr.hasNext()) { 
                  Server server = itr.next(); 
                  if (server.getName().equals(serverName)) {
                    System.out.println("Removing: " + server.getName());
                    itr.remove();
                  }
                }
              }

            // Command: Server Ping
            } else if (command.substring(3, 6).equals("PIN")) {
              synchronized(Storage.getServerList()) {
                String serverName = command.substring(6);
                Iterator<Server> itr = Storage.getServerList().iterator();

                while (itr.hasNext()) { 
                  Server server = itr.next(); 
                  if (server.getName().equals(serverName)) {
                    server.ping();
                  }
                }
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}