package com.company;

import java.io.*;

/**
 * Main class, it only starts a ServerController
 */


public class Main{


    public static void main(String[] args) throws IOException
    {
        ServerController myServer = ServerController.getInstance();
        System.out.println("STARTING");
        myServer.startServer(8080);
    }
}
