package client.controller;

import client.manage.ClientThread;
import client.manage.ManageClientThreadHash;
import client.tools.ReturnMessagePackage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class AddQQ {

    @FXML
    private Button addFriend;

    @FXML
    private AnchorPane an;

    @FXML
    private TextField qqText;

    public String sender;
    @FXML
    void addButton(MouseEvent event) throws IOException {
        ReturnMessagePackage rmg = new ReturnMessagePackage();
        rmg.setMessagePackage("16");
        rmg.setConText(qqText.getText());
        rmg.setSender(sender);
        ClientThread thread = ManageClientThreadHash.getThread(sender);
        ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
        oos.writeObject(rmg);
    }

}
