package com.company;
import java.util.*;
/**
 * Created by John on 4/29/2017.
 */
public class RoomManager {
    private static RoomManager ourInstance = new RoomManager();
    public static RoomManager getInstance() {
        return ourInstance;
    }
    private RoomManager() {
        rooms.add(new Room("Default_1"));
        rooms.add(new Room("Default_2"));
        rooms.add(new Room("Default_3"));
    }
    private List<Room> rooms = new LinkedList<Room>();


    public List<Room> GetRooms() {
        return rooms;
    }
    public boolean AddRoom(Room tmp) {
        return true;
    }

    public boolean JoinRoom(String roomName,User user) {
        if(user.getUserRoom() != null) {
            user.getUserRoom().removeUser(user);
        }
        for(Room i : rooms) {
            if(i.getName().equals(roomName)) {
                i.addUser(user);
                user.AssignRoom(i);
                return true;
            }
        }
        return false;
    }
    /*public boolean RemoveRoom(Room tmp) {
        return true;
    }*/


}
