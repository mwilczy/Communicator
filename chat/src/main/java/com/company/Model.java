package com.company;

import java.util.List;

/**
 * Created by John on 4/29/2017.
 */
public class Model {
    private RoomManager roomManager_;
    private ClientManager clientManager_;
    Model() {
        roomManager_ = RoomManager.getInstance();
        clientManager_ = ClientManager.getInstance();
    }
    User AddUser(String nickname) {

        User myUser = new User(nickname);
        if(clientManager_.AddUser(myUser)) {
            return myUser;
        }
        else {
            return null;
        }
    }
    boolean RemoveUser(User curUser) {
        return clientManager_.RemoveUser(curUser);
    }
    List<Room> GetAllRooms() {
        return roomManager_.GetRooms();
    }
    boolean JoinRoom(String userName,User user){
        return roomManager_.JoinRoom(userName,user);
    }
}
