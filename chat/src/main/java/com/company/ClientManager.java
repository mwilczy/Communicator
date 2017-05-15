package com.company;
import java.util.*;
/**
 * singleton used by Model to manage clients
 */
class ClientManager {

    private List<User> users_ = new LinkedList<User>();

    private static ClientManager ourInstance = new ClientManager();
    static ClientManager getInstance() {
        return ourInstance;
    }
    private ClientManager() {}

    /**
     * tries to add a new user
     * @param user user to add
     * @return if succesful returns true , else if nick is already taken returns false
     */
    boolean AddUser(User user) {
        for(User i : users_) {
            if(i.getNickName().equals(user.getNickName()))
                return false;
        }
        users_.add(user);
        return true;
    }

    /**
     * tries to remove user
     * @param user user to remove
     * @return if succesful returns true , else user doesn't exist returns false
     */
    boolean RemoveUser(User user) {
        if(users_.contains(user)) {
            user.removeFromRoom();
            users_.remove(user);
            return true;
        }
        return false;
    }
}
