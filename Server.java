public class Server {

  private String Name;
  private String IP;
  private int Port;

  private long Ping;
  
  public Server(String Name, String IP, int Port) {
    this.IP = IP;
    this.Port = Port;
    this.Name = Name;
    this.Ping = System.currentTimeMillis();
  }

  public String getName() {
    return Name;
  }

  public String getIP() {
    return IP;
  }

  public int getPort() {
    return Port;
  }

  public void ping() {
    this.Ping = System.currentTimeMillis();
  }

  public long getPastTimePing() {
    return System.currentTimeMillis() - Ping;
  }
}