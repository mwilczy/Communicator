package com.company;

import javax.json.*;
import java.net.Socket;
import java.util.List;

/**
 * connectionController is created for every connection,
 * takes input from ControllerView and responds appropriately
 *
 */
class ConnectionController {
    private Model myData;
    private JsonObjectBuilder reqBuilder_;
    private User myUser_;
    private Socket clientSocket_;
    ConnectionController(Model data, Socket clientSocket) {
        myData = data;
        reqBuilder_ = Json.createObjectBuilder();
        clientSocket_ = clientSocket;
    }

    /**
     * function that parses protocol and calls appropriate function to handle requests
     * @param msg input message
     * @return output message
     */
    JsonObject parseMessage(JsonObject msg) {
        String action;
        action = msg.getString("action");
        System.out.println("PARSE MESSAGE " + action);
        switch(action) {
            case "SendNick":
                return responseUserName(msg.getString("nick"));
            case "RequestRooms":
                return responseRoomList();
            case "JoinRoom":
                return responseJoinRoom(msg.getString("roomName"));
            case "SendMessage":
                return responseSendMessage(msg.getString("message"));
        }
        return null;
    }
    private JsonObject responseSendMessage(String message) {
        System.out.println("got message");
        reqBuilder_.add("action","OK");
        if(myUser_.hasRoom()) {
            BroadCastTextMessage(myUser_.getUserRoom().getAllUsers(),message);
        }
        return reqBuilder_.build();
    }
    private JsonObject responseJoinRoom(String roomName) {
        reqBuilder_.add("action","JoinRoom");
        reqBuilder_.add("roomName",roomName);
        Room prevRoom = myUser_.getUserRoom();
        if(myData.JoinRoom(roomName,myUser_)) {
            if(prevRoom != null) {
                System.out.println("Broadcast remove user");
                BroadCastRemoveUser(prevRoom.getAllUsers(), myUser_.getNickName());
            }
            BroadCastNewUser(myUser_.getUserRoom().getAllUsers(),myUser_.getNickName());
            reqBuilder_.add("decision", "granted");
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for(User i : myUser_.getUserRoom().getAllUsers()) {
                arrayBuilder.add(i.getNickName());
            }
            reqBuilder_.add("users",arrayBuilder);
        }
        else
            reqBuilder_.add("decision","rejected");
        return reqBuilder_.build();
    }
    private JsonObject responseUserName(String nickname) {
        reqBuilder_.add("action","SendNick");
        User tmpUser;
        if((tmpUser = myData.AddUser(nickname) )!= null) {
            myUser_ = tmpUser;
            myUser_.setUserSocket(clientSocket_);
            reqBuilder_.add("decision","granted");
        }
        else {
            reqBuilder_.add("decision","occupied");
        }
        return reqBuilder_.build();
    }

    private JsonObject responseRoomList() {
        reqBuilder_.add("action","RequestRooms");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Room myRoom :myData.GetAllRooms()) {
            arrayBuilder.add(myRoom.getName());
        }
        reqBuilder_.add("rooms",arrayBuilder);
        return reqBuilder_.build();
    }

    private void BroadCastNewUser(List<User> users,String message) {
        JsonObjectBuilder req = Json.createObjectBuilder();
        req.add("action","UserUpdate");
        req.add("name",message);
        BroadCastMessage(users,req.build());
    }
    private void BroadCastRemoveUser(List<User> users, String message) {
        JsonObjectBuilder req = Json.createObjectBuilder();
        req.add("action","UserRemove");
        req.add("name",message);
        BroadCastMessage(users,req.build());
    }
    private void BroadCastTextMessage(List<User> users, String message) {
        JsonObjectBuilder req = Json.createObjectBuilder();
        req.add("action", "SendMessage");
        req.add("message", message);
        req.add("user", myUser_.getNickName());
        BroadCastMessage(users, req.build());
    }
    private void BroadCastMessage(List<User> users,JsonObject message) {
        for (User i : users) {
            if(i == myUser_)
                continue;
            try {
                Socket tmp = i.getUserSocket();
                if (tmp != null && tmp.isConnected()) {
                    JsonWriter myWriter = Json.createWriter(tmp.getOutputStream());
                    System.out.println(message);
                    myWriter.writeObject(message);
                }
            } catch (Exception e) {
                System.out.println("bug " + e.getMessage());
            }
        }
    }

    void cleanUp() {
        myData.RemoveUser(myUser_);
    }
}
