package client.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Test extends Application {

    @FXML
    private TextField Text;

    @FXML
    void enter(KeyEvent event) {
        if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
            System.out.println("按住了enter键");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Test.fxml"));
        stage.setTitle("QQ");
        stage.getIcons().add(new Image("file:\\D:\\idea\\QQ\\libr\\QQimage.jpg"));
        stage.setResizable(false);
        stage.setScene(new Scene(root, 624, 484));
        stage.show();
    }
}
