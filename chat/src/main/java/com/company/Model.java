package com.company;

import java.util.List;

/**
 * singleton meant to store data and provide means to manipulate it
 */
class Model {
    private RoomManager roomManager_;
    private ClientManager clientManager_;
    Model() {
        roomManager_ = RoomManager.getInstance();
        clientManager_ = ClientManager.getInstance();
    }

    /**
     * interface to adding new user,
     * calls ClientManager AddUser method
     * @param nickname nick of the wanna-be user
     * @return returns new user if succesful or null if nick is already taken
     */
    synchronized User AddUser(String nickname) {
        User myUser = new User(nickname);
        if(clientManager_.AddUser(myUser)) {
            return myUser;
        }
        else {
            return null;
        }
    }

    /**
     * interface to removing users
     * @param curUser user to remove
     * @return true if succesfuly removed, else false
     */
    synchronized boolean RemoveUser(User curUser) {
        return clientManager_.RemoveUser(curUser);
    }

    /**
     *
     * @return returns List of rooms
     */
    synchronized List<Room> GetAllRooms() {
        return roomManager_.GetRooms();
    }

    /**
     * tries to connect user with room
     * @param roomName room to connect
     * @param user user attempting to connect
     * @return true if successfully paired user with a room, else false
     */
    synchronized boolean JoinRoom(String roomName,User user){
        return roomManager_.JoinRoom(roomName,user);
    }
}
