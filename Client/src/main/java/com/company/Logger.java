package com.company;

/**
 * Created by John on 4/30/2017.
 */
public class Logger {
    Main mainWindow_;
    public Logger(Main mainWindow) {
        mainWindow_ = mainWindow;
    }
    synchronized void AppendText(String myText) {
        mainWindow_.AppendText(myText + "\n");
    }
    synchronized void AppendMessageText(String myText) {
        mainWindow_.AppendMessageText(myText);
    }

}
