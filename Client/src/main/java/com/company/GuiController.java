package com.company;

import java.util.List;

/**
 * Created by John on 5/1/2017.
 */
public class GuiController {
    private Main mainWindow_;
    GuiController(Main mainWindow) {
        mainWindow_ = mainWindow;
    }
    synchronized void EnableRooms() {
        mainWindow_.EnableRoomView();
    }
    synchronized void DisableRooms() {
        mainWindow_.DisableRoomView();
    }
    synchronized void UpdateRooms() {
        mainWindow_.UpdateRooms();
    }
    synchronized void UpdateUsers() {mainWindow_.UpdateUsers();}
    synchronized void EnableDisconnect() {mainWindow_.EnableDisconnect();}
    synchronized void DisableDisconnect() {mainWindow_.DisableDisconnect();}
}
