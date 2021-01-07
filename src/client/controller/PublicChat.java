package client.controller;

import client.manage.ClientThread;
import client.manage.ManageClientThreadHash;
import client.tools.ChatData;
import client.tools.GUI;
import client.tools.ReturnMessagePackage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicChat {

    @FXML
    private Label sendFile;

    @FXML
    private Label voice;

    @FXML
    private TextArea sendTextArea;

    @FXML
    private Label ofen;

    @FXML
    private Label emoji;

    @FXML
    private AnchorPane ChatPanel;

    @FXML
    public ListView<ChatData> showListView;

    @FXML
    private Label picture;

    @FXML
    private Button sendButton;

    public String sender;

    public String receiver;

    public Stage stage;

    public String qqNumber;

    @FXML
    void addFileListener(MouseEvent event) throws Exception{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择文件");
        chooser.getExtensionFilters().addAll((new FileChooser.ExtensionFilter("所有文件","*.*")));
        File file = chooser.showOpenDialog(stage);

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] b = new byte[(int) file.length()];
        fileInputStream.read(b);
        System.out.println("选择的文件大小为:"+b.length);

        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).getSocket().getOutputStream());
        ReturnMessagePackage rmg = new ReturnMessagePackage();
        rmg.setMessagePackage("12");
        rmg.setDate(new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date()));
        rmg.setSender(sender);
        rmg.setGolder(receiver);
        rmg.setFile(b);
        String[] s = file.toString().split("\\\\");
        System.out.println(s[s.length-1]);
        String[] type = s[s.length - 1].split("\\.");
        System.out.println(type[1]);
        rmg.setFileType('.'+type[1]);
        rmg.setFileName(s[s.length-1]);

        oos.writeObject(rmg);
    }

    @FXML
    void addAudio(MouseEvent event)throws Exception {
        FXMLLoader load = new GUI(new Stage(), "../view/QQAudio.fxml", "录制音频", 449, 192).getFXMLLoader();
        Object controller = load.getController();
        StartAudio startAudio = (StartAudio) controller;
        startAudio.sender = sender;
        startAudio.receive = receiver;
        startAudio.privateOrPublic = "13";
        startAudio.oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).getSocket().getOutputStream());
    }

    @FXML
    void addListener(MouseEvent event) throws Exception{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择图片文件");
        chooser.getExtensionFilters().addAll((new FileChooser.ExtensionFilter("图片文件","*.jpg")),
                new FileChooser.ExtensionFilter("png图片","*.png"));
        File file = chooser.showOpenDialog(stage);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] b = new byte[(int) file.length()];
        fileInputStream.read(b);
        System.out.println("选择的文件大小为:"+b.length);
        byte[] bytes = file.toString().getBytes();

        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).getSocket().getOutputStream());
        ReturnMessagePackage rmg = new ReturnMessagePackage();
        rmg.setMessagePackage("11");
        rmg.setDate(new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date()));
        rmg.setSender(sender);
        rmg.setGolder(receiver);
        rmg.setFile(b);
        String[] s = file.toString().split("\\\\");
        System.out.println(s[s.length-1]);
        String[] type = s[s.length - 1].split("\\.");
        System.out.println(type[1]);
        rmg.setFileType('.'+type[1]);
        String array = new BigInteger(1,bytes).toString(16);
        System.out.println(array);

        rmg.setArray(array);
        oos.writeObject(rmg);

    }

    @FXML
    void sendMessage(MouseEvent event) throws IOException {
        ClientThread clientThread = ManageClientThreadHash.getThread(sender);
        ReturnMessagePackage rmg = new ReturnMessagePackage();
        rmg.setMessagePackage("10");
        rmg.setConText(sendTextArea.getText());
        sendTextArea.clear();
        String format = new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date());
        rmg.setDate(format);
        rmg.setSender(sender);
        rmg.setGolder(receiver);
        ObjectOutputStream oos =new ObjectOutputStream(clientThread.s.getOutputStream());
        oos.writeObject(rmg);

        File file = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender);
        if(!file.exists()){
            file.mkdirs();
        }
        File fileLog = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender+"\\LogPublic"+sender+"to"+qqNumber+".text");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileLog,true)));
        bw.write(sender +" "+qqNumber);
        bw.newLine();
        bw.write(format);
        bw.newLine();
        bw.write(rmg.getConText());
        bw.newLine();
        bw.flush();
    }
}
