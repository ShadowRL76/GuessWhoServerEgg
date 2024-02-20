import java.util.ArrayList;

public class Storage {
    private static ArrayList<Server> serverList = new ArrayList<Server>();

    public static ArrayList<Server> getServerList() {
        return serverList;
    }

    public static void setServerList(ArrayList<Server> list) {
        serverList = list;
    }
}
