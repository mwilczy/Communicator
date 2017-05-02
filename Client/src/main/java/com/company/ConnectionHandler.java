package com.company;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import javax.json.*;



/**
 * Created by John on 4/30/2017.
 */
public class ConnectionHandler extends Thread{
    private Model data_;
    private Logger log_;
    private MessageDispatcher myDispatcher_;
    private GuiController myGuiController_;
    Socket mySocket;
    public void run() {
        Connect();
    }
    ConnectionHandler(Model data,Logger log,GuiController myGuiController) {
        data_ = data;
        log_ = log;
        myGuiController_ = myGuiController;
    }

    private void CloseConnection() {
        try {
            mySocket.close();
            myGuiController_.DisableDisconnect();
            myGuiController_.DisableRooms();
            log_.AppendText("[ServerLog] Connection terminated");
        }
        catch(Exception e) {

        }
    }
    private void startConnection() throws Exception{
        log_.AppendText("[ServerLog] Trying to connect to: " + data_.getServerIP() + ":" + data_.getServerPort());
        mySocket = new Socket();
        mySocket.connect(new InetSocketAddress("127.0.0.1",8080),1000);
        data_.setSocket(mySocket);
        log_.AppendText("[ServerLog] Connected");
        myGuiController_.EnableDisconnect();
    }
    private void performInitialProtocol() throws Exception {
        myDispatcher_ = new MessageDispatcher(data_,mySocket);
        myGuiController_.EnableRooms();

        if(myDispatcher_.RequestNick()) {
            log_.AppendText("[ServerLog] Nick granted");
        }
        else {
            log_.AppendText("[ServerLog] Nick not available, please choose another one");
            throw new Exception("Nick already taken");
        }
        myDispatcher_.RequestRooms();
        myGuiController_.UpdateRooms();
    }
    private void receiveMessagesLoop() throws Exception {
        while(true) {
            JsonReader jsonReader = Json.createReader(mySocket.getInputStream());
            JsonObject msg = jsonReader.readObject();
            if(msg.getString("action").equals("SendMessage")) {
                log_.AppendText(msg.getString("user") + ": " + msg.getString("message"));
            }
            if(msg.getString("action").equals("JoinRoom")) {
                if(msg.getString("decision").equals("granted")) {
                    log_.AppendText("[ServerLog] Succesfuly connected to room");
                    data_.setCurrentRoom(msg.getString("roomName"));
                    JsonArray array = msg.getJsonArray("users");
                    List<String> allUsers = new LinkedList<String>();
                    for(int i=0;i<array.size();++i) {
                        allUsers.add(array.getString(i));
                    }
                    data_.setUserList(allUsers);
                    myGuiController_.UpdateUsers();
                }
            }
            if(msg.getString("action").equals("UserUpdate")) {
                data_.getUserList().add(msg.getString("name"));
                myGuiController_.UpdateUsers();
            }
            if(msg.getString("action").equals("UserRemove")) {
                for(int i=0;i<data_.getUserList().size();++i) {
                    if(data_.getUserList().get(i).equals(msg.getString("name"))) {
                        data_.getUserList().remove(i);
                        break;
                    }
                }
                myGuiController_.UpdateUsers();
            }
            System.out.println(msg);
        }
    }
    private boolean Connect() {
        try {
            startConnection();
            performInitialProtocol();
            receiveMessagesLoop();
        }
        catch(Exception e) {
        }
        CloseConnection();
        return true;
    }
}
