package client.manage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 登录主界面，用于启动qq登录界面
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {  //主界面窗口
        Parent root = FXMLLoader.load(getClass().getResource("../view/QQlogin.fxml"));
        primaryStage.setTitle("QQ");
        primaryStage.getIcons().add(new Image("file:\\D:\\idea\\QQ\\libr\\QQimage.jpg"));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 624, 484));
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {
        launch(args);//创建主登录窗口
    }
}
