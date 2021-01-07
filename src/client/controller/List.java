package client.controller;
/**
 * 控制类：控制list界面，当单击每一个button时候（可能需要改进），进入聊天界面
 * 同时得到List界面的名字，并且获得聊天界面的控制器得到loader，目的是取得每个界面的textArea，然后将每个textArea通过Chat名字的方式
 * 保存到HashMap中，方便Chat类过去对应的textArea显示数据
 */

import client.manage.ClientThread;
import client.manage.ManageClientThreadHash;
import client.tools.ChatData;
import client.tools.GUI;
import client.tools.ListData;
import client.tools.ReturnMessagePackage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class List {
    @FXML
    private  AnchorPane controllerList;

    @FXML
    public TextField ListSign;

    @FXML
    public TextField ListName;

    @FXML
    public ImageView head;

    @FXML
    private Label addFriend;

    @FXML
    private Label info;

    @FXML
    public ListView<ListData> listView;

    @FXML
    private Button ListFriend;

    @FXML
    private Button publicChatButton;

    public String listAccount;

    public Stage friendStage;

    public Stage publicChatStage;

    public String sender;

    @FXML
    public ListView<? > getListView(){
        if(listView == null) System.out.println("listview空");
        return  listView;
    }

    @FXML
    void ListToChat(MouseEvent event) throws IOException {
        if(event.getClickCount()>=2){
            ListData selectedItem = (ListData)listView.getSelectionModel().getSelectedItem();
            Stage stage = (Stage)controllerList.getScene().getWindow();
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader = new GUI(stage1, "../view/QQChat.fxml", listAccount + " 和 " + selectedItem.getName() + " 正在聊天", 600, 400).getFXMLLoader();
            Object controller = fxmlLoader.getController();
            Chat chat = (Chat)controller;
            chat.stage = stage1;

            chat.ChatPanel.setStyle(" -fx-background-color: white");
            chat.sender = listAccount;
            chat.receive = selectedItem.getName();
            ClientThread thread = ManageClientThreadHash.getThread(sender);
            thread.listView= (ListView<ChatData>)chat.showListView;


            File file1 = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender);//读取聊天记录
            if(!file1.exists()){
                file1.mkdirs();
            }
            File file = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender+"\\"+"Log"+sender+"to"+selectedItem.getName()+".text");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String containData;
            String logSender,logReceiver,logData,logText;
            while((containData = br.readLine())!=null){
                String[] s = containData.split(" ");
                logSender = s[0];
                logReceiver = s[1];
                logData = br.readLine();
                logText = br.readLine();
                thread.listView.getItems().addAll(new ChatData(3,logText,logData,logSender,logReceiver));
            }




            thread.listView.setCellFactory(new Callback<ListView<ChatData>, ListCell<ChatData>>() {
                @Override
                public ListCell<ChatData> call(ListView<ChatData> chatDataListView) {
                    ListCell<ChatData> listCell = new ListCell<ChatData>(){
                        @Override
                        protected void updateItem(ChatData item, boolean empty) {
                            super.updateItem(item, empty);

                            if(!empty && item!=null){
                                if(item.getisFile() == 3){
                                    VBox box = new VBox();
                                    Label label = new Label();
                                    if(item.getSender().equals(sender)){
                                        label.setText("自己"+"："+item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }else {
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }
                                    Label label1 = new Label();

                                    StringBuffer s = new StringBuffer(item.getMess());
                                    for(int i=10; i<item.getMess().length(); i+=10){
                                        s.insert(i,"\r\n");
                                    }
                                    label1.setText(s.toString());
                                    box.getChildren().addAll(label,label1);
                                    this.setGraphic(box);
                                }else if(item.getisFile() == 4){
                                    VBox box = new VBox();
                                    Label label = new Label();
                                    if(item.getSender().equals(sender)){
                                        label.setText("自己"+"："+item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }else {
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }
                                    ImageView imageView = item.getImage();
                                    imageView.setFitHeight(200);
                                    imageView.setPreserveRatio(true);
                                    box.getChildren().addAll(label,imageView);
                                    this.setGraphic(box);
                                }else if(item.getisFile() == 5){
                                    VBox box = new VBox();
                                    Label label = new Label();
                                    if(item.getSender().equals(sender)){
                                        label.setText("自己"+"："+item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }else {
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }
                                    Label label1 = new Label();
                                    label1.setText(item.getMess());
                                    label1.setStyle("-fx-text-fill:#87c20c;-fx-font-size: 10;");
                                    box.getChildren().addAll(label,label1);
                                    this.setGraphic(box);
                                }else if(item.getisFile() == 6){
                                    VBox box = new VBox();
                                    Label label = new Label();
                                    if(item.getSender().equals(sender)){
                                        label.setText("自己"+"："+item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }else {
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }
                                    Button button = new Button();
                                    button.setText(item.getMess());
                                    System.out.println("音频");
                                    button.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {
                                            URL url = null;
                                            try {
                                                url = new URL(item.getFileName());
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }
                                            AudioClip clip = new AudioClip(url.toExternalForm());
                                            clip.play();
                                        }
                                    });

                                    box.getChildren().addAll(label,button);
                                    this.setGraphic(box);
                                }
                            }else if(empty){
                                setText(null);
                                this.setGraphic(null);
                            }
                        }
                    };
                    return  listCell;
                }
            });
        }
    }

    @FXML
    void friend(MouseEvent event) {
        if(publicChatStage != null){
            publicChatStage.hide();
        }
        friendStage.show();
    }

    @FXML
    void publicChat(MouseEvent event) throws IOException {
        if(publicChatStage == null){
            Stage stage = new Stage();
            stage.setX(friendStage.getX());
            stage.setY(friendStage.getY());
            FXMLLoader loader = new GUI(stage, "../view/QQListPublic.fxml", sender + "的聊天界面", 439, 735).getFXMLLoader();
            ListPublicChat list = (ListPublicChat)loader.getController();
            list.sender = sender;
            list.ListName.setText(ListName.getText());
            list.ListSign.setText(ListSign.getText());
            ClientThread thread = ManageClientThreadHash.getThread(sender);
            thread.publicChatList = list.listViewPublicChat;




            list.listViewPublicChat.setCellFactory(new Callback<ListView<ListData>, ListCell<ListData>>() {
                @Override
                public ListCell<ListData> call(ListView<ListData> listView) {
                    ListCell<ListData> listCell = new ListCell<ListData>(){
                        @Override
                        protected void updateItem(ListData item, boolean empty) {
                            super.updateItem(item, empty);
                            if(!empty && item!=null){
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
                            }else if(empty){
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    };
                    return listCell;
                }
            });
            ReturnMessagePackage rmg = new ReturnMessagePackage();
            rmg.setMessagePackage("7");
            rmg.setSender(sender);
            list.friendStage = friendStage;
            list.publicChatStage = stage;
            list.head.setImage(head.getImage());
            publicChatStage = stage;
            friendStage.hide();

            ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).s.getOutputStream());
            oos.writeObject(rmg);
        }else{
            friendStage.hide();
            publicChatStage.show();
        }
    }
    @FXML
    void ReleaseSign(KeyEvent event) throws IOException {
        if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
            ReturnMessagePackage rmg = new ReturnMessagePackage();
            rmg.setConText(ListSign.getText());
            rmg.setMessagePackage("15");
            rmg.setSender(sender);
            ClientThread thread = ManageClientThreadHash.getThread(sender);
            ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
            oos.writeObject(rmg);
            controllerList.requestFocus();
        }
    }

    @FXML
    void ReleaseName(KeyEvent event) throws Exception{
        if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
            ReturnMessagePackage rmg = new ReturnMessagePackage();
            rmg.setConText(ListName.getText());
            rmg.setMessagePackage("14");
            rmg.setSender(sender);
            ClientThread thread = ManageClientThreadHash.getThread(sender);
            ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
            oos.writeObject(rmg);
            controllerList.requestFocus();
            Stage stage = (Stage)controllerList.getScene().getWindow();
            stage.setTitle(ListName.getText()+"("+sender+")"+" 的聊天界面");
        }
    }
    @FXML
    void addFriend(MouseEvent event) {
        FXMLLoader loader = new GUI(new Stage(), "../view/QQAddFriend.fxml", "添加好友", 489, 125).getFXMLLoader();
        AddQQ add = (AddQQ)loader.getController();
        add.sender = sender;
    }
}
