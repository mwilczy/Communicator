package com.company;

import java.net.Socket;

/**
 * represents user connected to the server,
 * contains his name and also room he's assigned to
 */
class User {
    private Socket userSocket_;
    private String nickName_;
    private Room myRoom = null;

    User(String nick) {
        nickName_ = nick;
    }

    /**
     * checks if user joined room
     * @return true if user currently in room, else false
     */
    boolean hasRoom() {
        return (myRoom != null);
    }

    /**
     * removes user from room
     */
    void removeFromRoom() {
        if(myRoom != null)
            myRoom.removeUser(this);
    }


    void setUserSocket(Socket userSocket) {
        userSocket_ = userSocket;
    }
    Socket getUserSocket() {
        return userSocket_;
    }

    String getNickName() {
        return nickName_;
    }

    /**
     * pair user with a room
     * @param assignedRoom room to be paired with
     */
    void AssignRoom(Room assignedRoom) {
        myRoom = assignedRoom;
    }
    Room getUserRoom() {
        return myRoom;
    }
}
