package com.company;

import javax.json.*;
import java.io.*;
import java.net.*;
/**
 * takes input from client, then calls ConnectionController,
 * which responds appropriately and then response is send over the network
 */
public class ConnectionView extends Thread {

    private Socket clientSocket_;
    private ConnectionController connectionController;
    public void run()
    {
        TalkToClient();
    }

    ConnectionView(Socket CliSocket, Model myData)
    {
        clientSocket_ = CliSocket;
        connectionController = new ConnectionController(myData,CliSocket);
    }

    private JsonObject handleProtocol(InputStream flow) {
        JsonObject myData;
        JsonReader myReader = Json.createReader(flow);
        myData = myReader.readObject();
        System.out.println(myData);
        return connectionController.parseMessage(myData);

    }

    private void TalkToClient() {
        try {
            while(true) {
                JsonObject response = handleProtocol(clientSocket_.getInputStream());
                JsonWriter myWriter = Json.createWriter(clientSocket_.getOutputStream());
                myWriter.writeObject(response);
            }
        }
        catch(Exception e) {
            System.out.println("clientHandler:" + e.getMessage());
        }
        // we need to cleanup here and remove user from his room
        connectionController.cleanUp();
        System.out.println("finished talking to client");
    }
}
