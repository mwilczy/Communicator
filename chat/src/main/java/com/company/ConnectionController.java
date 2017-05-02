package com.company;

import javax.json.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by John on 4/30/2017.
 */
public class ConnectionController {
    private Model myData;
    private JsonObjectBuilder reqBuilder;
    private User myUser_;
    Socket clientSocket_;
    public ConnectionController(Model data, Socket clientSocket) {
        myData = data;
        reqBuilder = Json.createObjectBuilder();
        clientSocket_ = clientSocket;
    }
    public JsonObject parseMessage(JsonObject msg) {
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
        reqBuilder.add("action","OK");
        if(myUser_.hasRoom()) {
            //myUser_.getUserRoom().BroadCastMessage(message, myUser_);
            BroadCastTextMessage(myUser_.getUserRoom().GetAllUsers(),message);
        }
        return reqBuilder.build();
    }
    private JsonObject responseJoinRoom(String roomName) {
        reqBuilder.add("action","JoinRoom");
        reqBuilder.add("roomName",roomName);
        Room prevRoom = myUser_.getUserRoom();
        if(myData.JoinRoom(roomName,myUser_)) {
            if(prevRoom != null) {
                System.out.println("Broadcast remove user");
                BroadCastRemoveUser(prevRoom.GetAllUsers(), myUser_.getNickName());
            }
            BroadCastNewUser(myUser_.getUserRoom().GetAllUsers(),myUser_.getNickName());
            reqBuilder.add("decision", "granted");
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for(User i : myUser_.getUserRoom().GetAllUsers()) {
                arrayBuilder.add(i.getNickName());
            }
            reqBuilder.add("users",arrayBuilder);
        }
        else
            reqBuilder.add("decision","rejected");
        return reqBuilder.build();
    }
    private JsonObject responseUserName(String nickname) {
        reqBuilder.add("action","SendNick");
        User tmpUser;
        if((tmpUser = myData.AddUser(nickname) )!= null) {
            myUser_ = tmpUser;
            myUser_.setUserSocket(clientSocket_);
            reqBuilder.add("decision","granted");
        }
        else {
            reqBuilder.add("decision","occupied");
        }
        return reqBuilder.build();
    }

    private JsonObject responseRoomList() {
        reqBuilder.add("action","RequestRooms");
        //reqBuilder.add("room","firstRoom");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Room myRoom :myData.GetAllRooms()) {
            arrayBuilder.add(myRoom.getName());
        }
        reqBuilder.add("rooms",arrayBuilder);
        return reqBuilder.build();
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
