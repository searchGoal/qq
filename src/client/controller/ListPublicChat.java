package client.controller;

import client.manage.ClientThread;
import client.manage.ManageClientThreadHash;
import client.tools.ChatData;
import client.tools.GUI;
import client.tools.ListData;
import client.tools.ReturnMessagePackage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;


public class ListPublicChat {

    @FXML
    public ImageView head;

    @FXML
    private AnchorPane controllerList;

    @FXML
    public TextField ListSign;

    @FXML
    public TextField ListName;

    @FXML
    private Button publicChatButton;

    @FXML
    private Button ListFriend;

    @FXML
    public ImageView bground;

    @FXML
    public ListView<ListData> listViewPublicChat;

    @FXML
    private Label addPublicChat;

    @FXML
    private Label creatPublic;

    @FXML
    private Label info;

    public Stage friendStage;

    public Stage publicChatStage;

    public String sender;

    @FXML
    void friend(MouseEvent event) {
        friendStage.setX(publicChatStage.getX());
        friendStage.setY(publicChatStage.getY());
        publicChatStage.hide();
        friendStage.show();
    }

    @FXML
    void publicChat(MouseEvent event) {

    }

    @FXML
    void ListToChat(MouseEvent event) throws Exception {
        if(event.getClickCount() >=2){
            Stage stage = new Stage();
            FXMLLoader loader = new GUI(stage, "../view/QQPublicChat.fxml", sender + " 在" + listViewPublicChat.getSelectionModel().getSelectedItem().getName() + " 群聊", 600, 400).getFXMLLoader();
            PublicChat publicChat = loader.getController();
            publicChat.sender = sender;
            publicChat.qqNumber = listViewPublicChat.getSelectionModel().getSelectedItem().getName();
            publicChat.stage = stage;
            publicChat.receiver = listViewPublicChat.getSelectionModel().getSelectedItem().getName();
            ClientThread thread = ManageClientThreadHash.getThread(sender);
            thread.publicChat = publicChat.showListView;


            File file1 = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender);
            if(!file1.exists()){
                file1.mkdirs();
            }
            File file = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender+"\\"+"LogPublic"+sender+"to"+listViewPublicChat.getSelectionModel().getSelectedItem().getName()+".text");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String containData;
            String logSender,logReceiver,logData,logText;
            while((containData = br.readLine())!=null){
                String[] s = containData.split(" ");
                System.out.println("s的大小"+s.length+"-->"+containData);
                logSender = s[0];
                logReceiver = s[1];
                logData = br.readLine();
                logText = br.readLine();
                thread.publicChat.getItems().addAll(new ChatData(3,logText,logData,logSender,logReceiver));
            }


            thread.publicChat.setCellFactory(new Callback<ListView<ChatData>, ListCell<ChatData>>() {
                @Override
                public ListCell<ChatData> call(ListView<ChatData> listView) {
                    ListCell<ChatData> listCell = new ListCell<ChatData>(){
                        @Override
                        protected void updateItem(ChatData item, boolean empty) {
                            super.updateItem(item, empty);

                            if(!empty && item!=null) {
                                if (item.getisFile() == 3) {

                                    ObservableList<ListData> items = thread.friendList.getItems();
                                    int flag = 0;
                                    Iterator<ListData> iterator = items.iterator();
                                    while(iterator.hasNext()){
                                        if(iterator.next().getName().equals(item.getSender())){
                                            flag = 1;
                                        }
                                    }
                                    if(item.getSender().equals(sender))flag =2;
                                    VBox box = new VBox();
                                    Label label = new Label();

                                    if(flag == 1){
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }else if(flag == 0){
                                        label.setText("陌生人" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#bd3bc2;-fx-font-size: 10;");
                                    }else if(flag == 2){
                                        label.setText("自己" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }
                                    Label label1 = new Label();

                                    StringBuffer s = new StringBuffer(item.getMess());
                                    for (int i = 10; i < item.getMess().length(); i += 10) {
                                        s.insert(i, "\r\n");
                                    }
                                    label1.setText(s.toString());
                                    box.getChildren().addAll(label, label1);
                                    this.setGraphic(box);
                                } else if (item.getisFile() == 4) {
                                    ObservableList<ListData> items = thread.friendList.getItems();
                                    int flag = 0;
                                    Iterator<ListData> iterator = items.iterator();
                                    while(iterator.hasNext()){
                                        if(iterator.next().getName().equals(item.getSender())){
                                            flag = 1;
                                        }
                                    }
                                    if(item.getSender().equals(sender))flag =2;
                                    VBox box = new VBox();
                                    Label label = new Label();

                                    if(flag == 1){
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }else if(flag == 0){
                                        label.setText("陌生人" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#bd3bc2;-fx-font-size: 10;");
                                    }else if(flag == 2){
                                        label.setText("自己" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }
                                    ImageView imageView = item.getImage();
                                    imageView.setFitHeight(200);
                                    imageView.setPreserveRatio(true);
                                    box.getChildren().addAll(label, imageView);
                                    this.setGraphic(box);
                                } else if (item.getisFile() == 5) {
                                    ObservableList<ListData> items = thread.friendList.getItems();
                                    int flag = 0;
                                    Iterator<ListData> iterator = items.iterator();
                                    while(iterator.hasNext()){
                                        if(iterator.next().getName().equals(item.getSender())){
                                            flag = 1;
                                        }
                                    }
                                    if(item.getSender().equals(sender))flag =2;
                                    VBox box = new VBox();
                                    Label label = new Label();

                                    if(flag == 1){
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }else if(flag == 0){
                                        label.setText("陌生人" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#bd3bc2;-fx-font-size: 10;");
                                    }else if(flag == 2){
                                        label.setText("自己" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
                                    }

                                    Label label1 = new Label();
                                    label1.setText(item.getMess());
                                    label1.setStyle("-fx-text-fill:#87c20c;-fx-font-size: 10;");
                                    box.getChildren().addAll(label, label1);
                                    this.setGraphic(box);
                                } else if (item.getisFile() == 6) {
                                    ObservableList<ListData> items = thread.friendList.getItems();
                                    int flag = 0;
                                    Iterator<ListData> iterator = items.iterator();
                                    while(iterator.hasNext()){
                                        if(iterator.next().getName().equals(item.getSender())){
                                            flag = 1;
                                        }
                                    }
                                    if(item.getSender().equals(sender))flag =2;
                                    VBox box = new VBox();
                                    Label label = new Label();

                                    if(flag == 1){
                                        label.setText(item.getSender() + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#5692c2;-fx-font-size: 10;");
                                    }else if(flag == 0){
                                        label.setText("陌生人" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#bd3bc2;-fx-font-size: 10;");
                                    }else if(flag == 2){
                                        label.setText("自己" + " : " + item.getData());
                                        label.setStyle("-fx-text-fill:#47c21c;-fx-font-size: 10;");
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
                                    setGraphic(box);
                                }
                            }else{
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    };

                    return listCell;
                }
            });
        }

    }
    @FXML
    void add(MouseEvent event) {
        FXMLLoader loader = new GUI(new Stage(), "../view/QQAddPublic.fxml", "添加群聊", 473, 180).getFXMLLoader();
        AddPublic add = (AddPublic)loader.getController();
        add.sender = sender;

    }

    @FXML
    void creat(MouseEvent event) throws IOException {
        ReturnMessagePackage rmg = new ReturnMessagePackage();
        rmg.setMessagePackage("8");
        rmg.setSender(sender);
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).s.getOutputStream());
        oos.writeObject(rmg);
    }
}
