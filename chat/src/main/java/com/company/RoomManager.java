package com.company;
import java.util.*;
/**
 * singleton used by Model to manage rooms
 */
class RoomManager {
    private static RoomManager ourInstance = new RoomManager();
    static RoomManager getInstance() {
        return ourInstance;
    }
    private RoomManager() {
        rooms.add(new Room("Default_1"));
        rooms.add(new Room("Default_2"));
        rooms.add(new Room("Default_3"));
    }
    private List<Room> rooms = new LinkedList<>();

    /**
     *
     * @return returns all rooms on server
     */
    List<Room> GetRooms() {
        return rooms;
    }

    /**
     * function called to join chosen room
     * @param roomName
     * @param user
     * @return true if joining was successful, else false
     */
    boolean JoinRoom(String roomName,User user) {
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

}
