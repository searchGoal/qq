package client.controller;

import client.manage.CheckUserInfo;
import client.manage.ClientThread;
import client.manage.ManageClientThreadHash;
import client.tools.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 控制类：控制主登录界面
 * 登录事件：当点击登录时，通过上一层checkUserinfo类验证账号密码是否正确，正确进入下一级菜单List
 */

public class Login {

    //登录界面
    @FXML
    private ImageView image;

    @FXML
    public PasswordField password;

    @FXML
    private Button forget;

    @FXML
    private Button enter;

    @FXML
    private TextField user;

    @FXML
    private Button register;

    @FXML
    void Action(MouseEvent event) throws Exception {//登录

        //封装User类，方便传递登录请求
        User user = new User();
        user.setAccount(this.user.getText());
        user.setPassword(this.password.getText());

        CheckUserInfo checkUserInfo = new CheckUserInfo();
        if(this.user.getText().equals("")||this.password.getText().equals("")){
            AlertScreen.getMessageScreen("警告","账号或密码为空，请重新输入");
        }else
        if(checkUserInfo.check(user)){
            Stage stage = new Stage();
            ReturnMessagePackage rmg= checkUserInfo.ct.rsg;
            FXMLLoader fxmlLoader = new GUI(stage, "../view/QQList.fxml", rmg.getName()+"("+user.getAccount()+")" + " 的聊天界面", 439, 735).getFXMLLoader();
            Object controller = fxmlLoader.getController();
            List qqList = (List)controller;//获取控制器
            qqList.friendStage = stage;
            qqList.sender = user.getAccount();


            ClientThread thread = ManageClientThreadHash.getThread(user.getAccount());
            thread.friendList = qqList.listView;
            thread.sender = user.getAccount();

            File file = new File("D:\\idea\\QQ\\libr\\head\\"+user.getAccount()+".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(rmg.getImage(user.getAccount()));
            qqList.head.setImage(new Image("file:\\D:\\idea\\QQ\\libr\\head\\"+user.getAccount()+".jpg"));


            qqList.listAccount = user.getAccount();//登录进入好友列表时表明是谁的好友列表
            String[] friendList = rmg.getFriendList().split("_");//得到好友列表
            qqList.ListName.setText(rmg.getName());
            if(checkUserInfo.ct.rsg.getSign()!=null){
                qqList.ListSign.setText(rmg.getSign());
            }
            ListView<ListData> listView = (ListView<ListData>)qqList.getListView();//通过控制器拿到listview
            ObservableList<ListData> obs = listView.getItems();
            listView.setFixedCellSize(60);

            for(int i=0; i<friendList.length && !friendList[i].equals(""); i++){//依次设置好友的头像
                System.out.println("-->"+friendList[i]);
                byte[] s = checkUserInfo.ct.rsg.getImage(friendList[i]);//从传过来的数据包中获取图像文件
                File file1 = new File("D:\\idea\\QQ\\libr\\head\\"+friendList[i]+".jpg");
                FileOutputStream out = new FileOutputStream(file1);
                out.write(s);//将头像文件写入到本地磁盘

                obs.add(new ListData(friendList[i],new ImageView("file:\\D:\\idea\\QQ\\libr\\head\\"+friendList[i]+".jpg")));
                //添加到自定义列表中
            }

            /**
             * 自定义listview
             */
            listView.setCellFactory(new Callback<ListView<ListData>, ListCell<ListData>>() {
                @Override
                public ListCell<ListData> call(ListView<ListData> listDataListView) {
                    ListCell<ListData> listCell = new ListCell<ListData>(){
                        @Override
                        protected void updateItem(ListData item, boolean empty) {
                            super.updateItem(item, empty);
                            if(!empty){
                                HBox hBox = new HBox(30);
                                hBox.setAlignment(Pos.CENTER_LEFT);
                                Label label = new Label(item.getName());
                                ImageView imageView = item.getImageView();
                                listView.setOrientation(Orientation.VERTICAL);
                                hBox.setNodeOrientation(NodeOrientation.INHERIT);
                                imageView.setFitWidth(40);
                                imageView.setPreserveRatio(true);
                                hBox.getChildren().addAll(imageView,label);
                                this.setGraphic(hBox);
                            }
                        }
                    };
                    return listCell;
                }
            });
        listView.requestFocus();
        }
    }
    @FXML
    void ActionRegister(MouseEvent event) throws Exception { //注册
        new GUI(new Stage(),"../view/QQregister.fxml","注册",522,595);//创建注册窗口
    }
    @FXML
    void forgotmouse(MouseEvent event) {//忘记密码
        System.out.println("a");
    }
}
