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
    void EnableRooms() {
        mainWindow_.EnableRoomView();
    }
    void DisableRooms() {
        mainWindow_.DisableRoomView();
    }
    void UpdateRooms() {
        mainWindow_.UpdateRooms();
    }
    void UpdateUsers() {mainWindow_.UpdateUsers();}
    void EnableDisconnect() {mainWindow_.EnableDisconnect();}
    void DisableDisconnect() {mainWindow_.DisableDisconnect();}
}
