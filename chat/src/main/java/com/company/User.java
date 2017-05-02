package com.company;

import java.net.Socket;

/**
 * Created by John on 4/29/2017.
 */
public class User {
    private Socket userSocket_;
    private String nickName_;
    private Room myRoom = null;
    public User() {
        nickName_ = "anonymous";
        userSocket_ = null;
    }
    User(String nick) {
        nickName_ = nick;
    }
    boolean hasRoom() {
        if(myRoom != null)
            return true;
        return false;
    }
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
    void setNickName(String nickName) {
        nickName_ = nickName;
    }
    String getNickName() {
        return nickName_;
    }
    void AssignRoom(Room assignedRoom) {
        myRoom = assignedRoom;
    }
    Room getUserRoom() {
        return myRoom;
    }
}
