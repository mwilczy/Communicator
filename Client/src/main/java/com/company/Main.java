package com.company;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;
import javafx.scene.input.MouseEvent;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.net.Socket;

public class Main extends Application {

    private Model data = Model.getInstance();
    private Logger myLoger;
    private GuiController myGuiController;
    private AsynchronousMessageSender mySender;
    private TextArea chat_output;
    private TextField chat_input;
    private TreeView<String> chat_view;
    private TreeItem<String> Rooms;
    private MenuItem itemDisconnect;
    public static void main(String args[]) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(this::HandleCloseApplication);
        primaryStage.setTitle("Communicator");

        myLoger = new Logger(this);
        myGuiController = new GuiController(this);
        mySender = new AsynchronousMessageSender(data);

        chat_output = new TextArea();
        chat_view = new TreeView<String>();
        chat_view.setOnMouseClicked(this::HandleRoomSelect);
        Rooms = new TreeItem<String>("Rooms");

        chat_view.setRoot(Rooms);
        chat_view.setDisable(true);
        chat_input = new TextField();
        chat_input.setOnAction(this::HandleSendMessage);

        chat_output.setText("Welcome to chat application ...\n");
        chat_output.setEditable(false);
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu menuConnect = new Menu("Network");
        MenuItem itemConnect = new MenuItem("Connect");
        itemConnect.setOnAction(this::HandleConnectClick);
        itemDisconnect = new MenuItem("Disconnect");
        itemDisconnect.setDisable(true);
        itemDisconnect.setOnAction(this::HandleDisconnectClick);
        menuConnect.getItems().addAll(itemConnect,itemDisconnect);


        Menu menuOptions = new Menu("Options");


        menuBar.getMenus().addAll(menuConnect,menuOptions);

        root.setCenter(chat_output);
        root.setBottom(chat_input);
        root.setRight(chat_view);
        root.setTop(menuBar);

        primaryStage.setScene(new Scene(root,800,400));


        primaryStage.show();

    }
    synchronized void AppendText(String myText) {
        chat_output.appendText(myText);
    }

    private void HandleSendMessage(ActionEvent event) {
        System.out.println(chat_input.getText());
        String txt = chat_input.getText();
        chat_input.clear();
        if(data.getSocket() != null && data.getSocket().isConnected()) {
            AppendText(data.getNickname() + ": " + txt);
            mySender.SendMessage(txt);
        }
    }
    private void HandleCloseApplication(WindowEvent event) {
        try {
            if(data.getSocket() != null)
                data.getSocket().close();
        }
        catch(Exception e) {

        }
    }
    private void HandleConnectClick(ActionEvent event) {
        if(ConnectForm.Show(data)) {
            System.out.println("OK");
            (new ConnectionHandler(data,myLoger,myGuiController)).start();
        }
    }
    private void HandleDisconnectClick(ActionEvent event) {
        if(data.getSocket() != null) {
            try {
                DisableRoomView();
                DisableDisconnect();
                data.getSocket().close();
            }
            catch(Exception e) {

            }
        }
    }
    private void HandleRoomSelect(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        if(node instanceof Text || (node instanceof TreeCell && ((TreeCell)node).getText() != null)) {
            if(event.getClickCount() == 2) {
                if(data.getSocket() == null || !data.getSocket().isConnected())
                {
                    Alert notConnected = new Alert(Alert.AlertType.INFORMATION);
                    notConnected.setTitle("Not Connected");
                    notConnected.setContentText("You're not connected to the server");
                    notConnected.show();
                    return;
                }
                String name = chat_view.getSelectionModel().getSelectedItem().getValue();
                System.out.println(name);
                myLoger.AppendText("[ServerLog] Trying to join room: " + name);
                mySender.SendJoinRoomRequest(name);
            }
        }
    }

    void EnableRoomView() {
        itemDisconnect.setDisable(false);
        chat_view.setDisable(false);
        chat_view.getRoot().setExpanded(true);
    }
    void DisableRoomView() {
        itemDisconnect.setDisable(true);
        chat_view.setDisable(true);
    }
    void UpdateRooms() {
        Rooms.getChildren().clear();
        for(String i : data.getRoomList()) {
            TreeItem<String> tmp = new TreeItem<String>(i);
            Rooms.getChildren().add(tmp);
        }
    }
    void UpdateUsers() {
        //TreeItem<String> roomUsers = new TreeItem<String>();

        String currentRoom = data.getCurrentRoom();
        for(TreeItem<String> roomNode :Rooms.getChildren()) {
            roomNode.getChildren().clear();
            roomNode.setExpanded(false);
            if(roomNode.getValue().equals(currentRoom)) {
                for(String myUser : data.getUserList()) {
                    roomNode.getChildren().add(new TreeItem<String>(myUser));
                }
                roomNode.setExpanded(true);
            }
        }
    }
    void EnableDisconnect() {
        itemDisconnect.setDisable(false);
    }
    void DisableDisconnect() {
        itemDisconnect.setDisable(true);
    }
}
