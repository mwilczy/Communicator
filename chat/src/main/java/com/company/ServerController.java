package com.company;
import java.net.*;
import java.io.*;
/**
 * Created by John on 4/28/2017.
 */
public class ServerController {
    private static ServerController ourInstance = new ServerController();

    static ServerController getInstance() {
        return ourInstance;
    }


    private Model myData_ = new Model();
    private int port_;

    boolean startServer(int port) {
        port_ = port;
        try {
            ServerSocket serverSocket = new ServerSocket(port_);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("new host connected");
                (new ConnectionView(clientSocket,myData_)).start();
            }

        }
        catch(IOException e)
        {
            System.out.println("ServerController error: " + e.getMessage());
        }
        return true;
    }
    private ServerController() {

    }

}
