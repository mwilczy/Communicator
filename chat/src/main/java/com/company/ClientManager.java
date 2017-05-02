package com.company;
import java.util.*;
/**
 * Created by John on 4/29/2017.
 */
public class ClientManager {

    private static ClientManager ourInstance = new ClientManager();
    static ClientManager getInstance() {
        return ourInstance;
    }
    private ClientManager() {}
    private List<User> users = new LinkedList<User>();
    boolean AddUser(User tmp) {
        for(User i : users) {
            if(i.getNickName().equals(tmp.getNickName()))
                return false;
        }
        users.add(tmp);
        return true;
    }
    boolean RemoveUser(User tmp) {
        if(users.contains(tmp)) {
            tmp.removeFromRoom();
            users.remove(tmp);
            return true;
        }
        return false;
    }
}
