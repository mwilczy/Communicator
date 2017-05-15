package com.company;
import java.net.*;
import java.io.*;
/**
 * singleton, listens for connections and starts a thread for each succesful connection
 */
class ServerController {
    private static ServerController ourInstance = new ServerController();

    static ServerController getInstance() {
        return ourInstance;
    }


    private Model myData_ = new Model();
    private int port_;

    /**
     * this function starts server
     * @param port port server will run on
     * @return
     */
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
