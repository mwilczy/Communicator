package com.company;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.net.Socket;

/**
 * Created by John on 5/2/2017.
 */
public class AsynchronousMessageSender {
    private Model data_;
    private JsonWriter myWriter;
    private JsonObjectBuilder req;
    public AsynchronousMessageSender(Model data) {
        data_ = data;
    }

    void initSending() throws Exception {
        myWriter = Json.createWriter(data_.getSocket().getOutputStream());
        req = Json.createObjectBuilder();
    }
    synchronized public void SendMessage(String txt) {
        try {
            initSending();
            req.add("action","SendMessage");
            req.add("message",txt);
            myWriter.writeObject(req.build());
        }
        catch(Exception e) {
            System.out.println("Error in AsynchronousMessageSender.SendMessage(): " + e.getMessage());
        }
    }
    synchronized public void SendJoinRoomRequest(String roomName) {
        try {
            initSending();
            req.add("action","JoinRoom");
            req.add("roomName",roomName);
            myWriter.writeObject(req.build());
        }
        catch(Exception e) {
            System.out.println("Error in AsynchronousMessageSender.SendJoinRoomRequest(): " + e.getMessage());
        }
    }
}
