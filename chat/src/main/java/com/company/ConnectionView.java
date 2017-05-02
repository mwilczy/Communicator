package com.company;

import javax.json.*;
import java.io.*;
import java.net.*;
/**
 * Created by John on 3/6/2017.
 */
public class ConnectionView extends Thread {

    private Socket ClientSocket;
    private ConnectionController myDispatcher;
    public void run()
    {
        TalkToClient();
    }

    ConnectionView(Socket CliSocket, Model myData)
    {
        ClientSocket = CliSocket;
        myDispatcher = new ConnectionController(myData,CliSocket);
    }

    JsonObject handleProtocol(InputStream flow) {
        JsonObject myData;
        JsonReader myReader = Json.createReader(flow);
        myData = myReader.readObject();
        System.out.println(myData);
        return myDispatcher.parseMessage(myData);

    }

    private void TalkToClient() {
        try {
            while(true) {
                JsonObject response = handleProtocol(ClientSocket.getInputStream());
                JsonWriter myWriter = Json.createWriter(ClientSocket.getOutputStream());
                myWriter.writeObject(response);
            }
        }
        catch(Exception e) {
            System.out.println("clientHandler:" + e.getMessage());
        }
        // we need to cleanup here and remove user from his room
        myDispatcher.cleanUp();
        System.out.println("finished talking to client");
    }
}
