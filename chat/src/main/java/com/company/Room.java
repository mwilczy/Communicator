package com.company;
import java.util.*;
/**
 * object representing room,
 * stores room name, and its users
 */
class Room {
    private List<User> users_ = new LinkedList<>();
    private String name_;
    Room(String name) {
        name_ = name;
    }
    String getName() {
        return name_;
    }

    /**
     * simply adds user to room
     * @param user
     * @return true if success, else false
     */
    boolean addUser(User user) {
        users_.add(user);
        return true;
    }

    /**
     * simply removes user from room
     * @param user
     * @return true if success, else false
     */
    boolean removeUser(User user) {
        users_.remove(user);
        return true;
    }
    List<User> getAllUsers() {
        return users_;
    }
}
