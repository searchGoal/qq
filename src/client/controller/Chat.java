package client.controller;
/**
 * 聊天界面：通过窗口获取到名字，从而得到相应名字的socket的输出流，进行发送数据
 * public TextArea getTextArea()；可要可不要，一开始是设置成private才写的get方法，若写成Public同样可以获取相应的textArea
 */

import client.manage.ManageClientThreadHash;
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

public  class Chat{
    @FXML
    private Label sendFile;

    @FXML
    private Label voice;

    @FXML
    private Label ofen;

    @FXML
    private Label emoji;

    @FXML
    private Label picture;

    @FXML
    private TextArea sendTextArea;

    @FXML
    public ListView<?> showListView;


    @FXML
    public AnchorPane ChatPanel;


    @FXML
    private Button sendButton;


    public String sender;
    public String receive;
    public Stage stage;
    public
    @FXML
    void sendMessage(MouseEvent event) {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).getSocket().getOutputStream());
            ReturnMessagePackage rmg = new ReturnMessagePackage();

            rmg.setMessagePackage("3");
            String format = new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date());
            rmg.setDate(format);
            rmg.setConText(sendTextArea.getText());
            rmg.setSender(sender);
            rmg.setGolder(receive);
            oos.writeObject(rmg);
            sendTextArea.clear();

            File file = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender);
            if(!file.exists()){
                file.mkdirs();
            }
            File fileLog = new File("D:\\idea\\QQ\\libr\\ChatLog"+sender+"\\Log"+sender+"to"+receive+".text");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileLog,true)));
            bw.write(sender +" "+receive);
            bw.newLine();
            bw.write(format);
            bw.newLine();
            bw.write(rmg.getConText());
            bw.newLine();
            bw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void addListener(MouseEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择图片文件");
        chooser.getExtensionFilters().addAll((new FileChooser.ExtensionFilter("图片文件","*.jpg")),
                new FileChooser.ExtensionFilter("png图片","*.png"));
        File file = chooser.showOpenDialog(stage);
        System.out.println("是否选择文件："+(file!=null));
        if(file != null){
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            fileInputStream.read(b);
            System.out.println("选择的文件大小为:"+b.length);
            byte[] bytes = file.toString().getBytes();

            ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).getSocket().getOutputStream());
            ReturnMessagePackage rmg = new ReturnMessagePackage();
            rmg.setMessagePackage("4");
            rmg.setDate(new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date()));
            rmg.setSender(sender);
            rmg.setGolder(receive);
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


    }

    @FXML
    void addFileListener(MouseEvent event) throws Exception {
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
        rmg.setMessagePackage("5");
        rmg.setDate(new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date()));
        rmg.setSender(sender);
        rmg.setGolder(receive);
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
    void addAudio(MouseEvent event) throws IOException {
        FXMLLoader load = new GUI(new Stage(), "../view/QQAudio.fxml", "录制音频", 449, 192).getFXMLLoader();
        Object controller = load.getController();
        StartAudio startAudio = (StartAudio) controller;
        startAudio.sender = sender;
        startAudio.receive = receive;
        startAudio.privateOrPublic = "6";
        startAudio.oos = new ObjectOutputStream(ManageClientThreadHash.getThread(sender).getSocket().getOutputStream());
    }
//    @Override
//    public void run() {//新的线程是ClientThread类
//        while(true){
//            try {
//                ObjectInputStream ois = new ObjectInputStream(ClientConnectServer.client.getInputStream());
//                ReturnMessagePackage s =(ReturnMessagePackage)ois.readObject();
//                System.out.println("接收到的信息是：  "+s.getConText()+"   --->"+Thread.currentThread());
//                textArea.appendText(s.getConText()+"\r\n");
//            } catch (IOException | ClassNotFoundException e) {
//                System.out.println("流并发错误");
//                e.printStackTrace();
//            }
//        }
//    }
}
