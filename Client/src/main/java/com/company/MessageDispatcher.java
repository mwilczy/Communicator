package com.company;

import jdk.internal.util.xml.impl.Input;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;
import static java.nio.channels.Selector.open;
import java.nio.channels.Channels;

/**
 * Created by John on 4/30/2017.
 */
public class MessageDispatcher {
    private Model myData;
    private JsonObjectBuilder reqBuilder;
    private InputStream in;
    private OutputStream out;
    private Socket mySocket_;
    public MessageDispatcher(Model data, Socket mySocket) throws IOException {
        myData = data;
        reqBuilder = Json.createObjectBuilder();
        mySocket_ = mySocket;
        in = mySocket.getInputStream();
        out = mySocket.getOutputStream();
    }
    public boolean RequestNick() throws Exception{
        reqBuilder.add("action","SendNick" );
        reqBuilder.add("nick",myData.getNickname());
        JsonObject response = SendAndGetResponse(reqBuilder.build());
        if(response.getString("action").equals("SendNick") && response.getString("decision").equals("granted")) {
            return true;
        }
        return false;
    }
    private JsonObject SendAndGetResponse(JsonObject req) throws Exception {
        JsonWriter jsonWriter = Json.createWriter(out);
        jsonWriter.writeObject(req);

        JsonReader jsonReader = Json.createReader(in);
        JsonObject response = jsonReader.readObject();
        System.out.println(response);
        return response;
    }
    public boolean RequestRooms() throws Exception{
        reqBuilder.add("action","RequestRooms");
        JsonObject response = SendAndGetResponse(reqBuilder.build());
        JsonArray roomsJson = response.getJsonArray("rooms");
        List <String> rooms = new LinkedList<String>();
        for(int i=0;i<roomsJson.size();++i) {
            rooms.add(roomsJson.getString(i));
        }
        myData.setRoomList(rooms);
        return true;
    }

}
