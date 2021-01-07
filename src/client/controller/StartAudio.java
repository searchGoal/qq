package client.controller;

import client.tools.ReturnMessagePackage;
import client.tools.SoundServer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartAudio {

    @FXML
    private AnchorPane cotroller;

    @FXML
    private Button startAudio;

    @FXML
    private Label show;

    @FXML
    private Button send;

    private int count = 0;

    public ObjectOutputStream oos;

    public String sender , receive ;

    private SoundServer audio;

    private String time;

    public String privateOrPublic;

    @FXML
    void start(MouseEvent event) throws Exception {
        if(count == 0){
            time = new BigInteger(new Date().toString().getBytes()).toString(16);
            audio = new SoundServer();
            audio.startRecoder(sender,receive,time);
            count = 1;
            show.setText("正在录制，请说话");
        }
    }

    @FXML
    void send(MouseEvent event) throws IOException {
        audio.endRecoder();
        ReturnMessagePackage rmg = new ReturnMessagePackage();
        rmg.setMessagePackage(privateOrPublic);
        rmg.setDate(new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date()));
        rmg.setSender(sender);
        rmg.setGolder(receive);

        File file = new File("D:\\idea\\QQ\\libr\\AudioFile"+sender+"\\"+time+".wav");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        while( fis.read(bytes) != -1){
            bos.write(bytes);
        }
        byte[] b = bos.toByteArray();
        rmg.setFile(b);
        System.out.println("录制的音频大小为："+b.length);

        rmg.setFileType(".wav");

        rmg.setFileName(time);

        oos.writeObject(rmg);
        Stage stage =(Stage)cotroller.getScene().getWindow();
        stage.close();
    }

}
