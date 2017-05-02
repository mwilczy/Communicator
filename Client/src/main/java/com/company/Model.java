package com.company;

import java.net.Socket;
import java.util.List;

/**
 * Created by John on 4/30/2017.
 */
public class Model {
    private static Model ourInstance = new Model();

    public static Model getInstance() {
        return ourInstance;
    }
    private String serverIP_;
    private int serverPort_;
    private String nickname_ = "";
    private List<String> roomList_ = null;
    private String currentRoom_ = null;
    private List<String> userList_ = null;
    private Socket currentSocket_ = null;
    private Model() {
    }

    synchronized void setSocket(Socket currentSocket) {
        currentSocket_ = currentSocket;
    }
    synchronized Socket getSocket() {
        return currentSocket_;
    }

    synchronized void setNickname(String nickname) {
        nickname_ = nickname;
    }
    synchronized String getNickname() {
        return nickname_;
    }
    synchronized void setServerIP(String serverIP) {
        serverIP_ = serverIP;
    }
    synchronized void setServerPort(int port) {
        serverPort_ = port;
    }
    synchronized String getServerIP() {
        return serverIP_;
    }
    synchronized int getServerPort() {
        return serverPort_;
    }

    synchronized void setRoomList(List <String>roomList) {
        roomList_ = roomList;
    }
    synchronized List<String>  getRoomList() {
        return roomList_;
    }

    synchronized void setUserList(List<String> userList) {
        userList_ = userList;
    }
    synchronized List<String> getUserList() {
        return userList_;
    }

    synchronized String getCurrentRoom() {
        return currentRoom_;
    }
    synchronized void setCurrentRoom(String currentRoom) {
        currentRoom_ = currentRoom;
    }

}
