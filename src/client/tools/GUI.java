package client.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 用于创建fxml生成的窗口
 * 构造方法：public GUI(Stage primaryStage,String path,String Title,double width,double heigh)
 * 方法：public FXMLLoader getFXMLLoader()，返回一个窗口的控制器
 *
 */
public class GUI {


    public FXMLLoader loader = null;

    private Stage stage = null;

    public GUI(Stage primaryStage,String path,String Title,double width,double heigh){
        try{
            this.stage = primaryStage;
            loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
//            Object controoller = loader.getController();
//            if(controoller instanceof Runnable){
//                new Thread((Runnable)controoller).start();
//            }
            primaryStage.setTitle(Title);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("file:\\D:\\idea\\QQ\\libr\\QQimage.jpg"));
            primaryStage.setScene(new Scene(root, width, heigh));
            primaryStage.show();
        }catch(Exception e){
            System.out.println("窗口创建出错");
            e.printStackTrace();
        }
    }
    public FXMLLoader getFXMLLoader(){
        return loader;
    }

    public Stage getStage(){
        return stage;
    }
}
