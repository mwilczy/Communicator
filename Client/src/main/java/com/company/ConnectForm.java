package com.company;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import javafx.scene.layout.GridPane;

/**
 * Created by John on 4/29/2017.
 */



public class ConnectForm {
    public static boolean Show(Model myData) {
        ReturnValue retVal = new ReturnValue();
        Stage connectStage = new Stage();
        connectStage.setTitle("Connect");
        connectStage.initModality(Modality.WINDOW_MODAL);
        GridPane root = new GridPane();
        Label nickLabel = new Label("Nickname");
        Label ipLabel = new Label("Server IP");
        Label portLabel = new Label("Port");
        TextField nickInput = new TextField();
        TextField ipInput = new TextField();
        TextField portInput = new TextField("8080");
        Button btn_OK = new Button("OK");
        btn_OK.setOnAction((event) -> {
            // check values
            myData.setServerIP(ipInput.getText());
            myData.setServerPort(Integer.parseInt(portInput.getText()));
            myData.setNickname(nickInput.getText());
            retVal.SetValue(true);
            connectStage.close();

        });
        btn_OK.setMaxWidth(1000.0);

        root.setHgap(10);
        root.setVgap(10);

        root.add(nickLabel,0,0);
        root.add(nickInput,1,0);
        root.add(ipLabel,0,1);
        root.add(ipInput,1,1);
        root.add(portLabel,0,2);
        root.add(portInput,1,2);
        root.add(btn_OK,1,3);
        root.setAlignment(Pos.CENTER);
        connectStage.setScene(new Scene(root,300,180));
        connectStage.showAndWait();

        return retVal.GetValue();
    }
}
