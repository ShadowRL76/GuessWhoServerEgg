import java.util.Iterator;

public class ListChecker extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            synchronized(Storage.getServerList()) {
                Iterator<Server> itr = Storage.getServerList().iterator();
  
                while (itr.hasNext()) { 
                    Server server = itr.next(); 
                    if (server.getPastTimePing() >= 5000) {
                        System.out.println("Removing: " + server.getName());
                        itr.remove();
                    }
                }
            }
        }
    }

}