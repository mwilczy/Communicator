package com.company;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.net.Socket;
import java.util.*;
/**
 * Created by John on 4/29/2017.
 */
public class Room {
    private int id_;
    private List<User> users = new LinkedList<User>();
    private String name_;
    Room(String name) {
        name_ = name;
    }
    String getName() {
        return name_;
    }
    boolean addUser(User user) {
        users.add(user);
        return true;
    }

    boolean removeUser(User user) {
        users.remove(user);
        return true;
    }
    List<User> GetAllUsers() {
        return users;
    }
}
