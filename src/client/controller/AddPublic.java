package client.controller;

import client.manage.ClientThread;
import client.manage.ManageClientThreadHash;
import client.tools.ReturnMessagePackage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;


public class AddPublic {

    @FXML
    private Button add;
    @FXML
    private AnchorPane an;

    @FXML
    private Label label;

    @FXML
    private TextField getPublic;

    public String sender;

    @FXML
    void addButton(MouseEvent event) throws IOException {
       if(getPublic.getText() != ""){
           ReturnMessagePackage rmg = new ReturnMessagePackage();
           rmg.setMessagePackage("9");
           rmg.setSender(sender);
           rmg.setConText(getPublic.getText());
           ClientThread thread = ManageClientThreadHash.getThread(sender);
           ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
           oos.writeObject(rmg);
           Stage stage = (Stage)an.getScene().getWindow();
           stage.close();
       }
    }

}
