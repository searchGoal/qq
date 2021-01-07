package client.manage;

import client.tools.*;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.Socket;

/**
 * 客户核心线程类：用于保持客户端的每个socket一直进行阻塞式读取操作
 */
public class ClientThread implements Runnable{

    public Socket s;
    public ListView<ChatData> listView;
    public ListView<ListData> publicChatList;
    public ListView<ChatData> publicChat;
    public ListView<ListData> friendList;
    public String sender;
    public ClientThread(Socket s){
        this.s = s;
    }
    public Socket getSocket(){
        return s;
    }
    @Override
    public void run() {

        //一直进行等待
        while(true){
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());//从socket中获取对象输入流
                ReturnMessagePackage rmg = (ReturnMessagePackage)ois.readObject();//读取数据包returnmessagepackage
                System.out.println("发送者："+rmg.getSender()+"  接收者："+rmg.getGolder()+"  接收到的信息是："+rmg.getConText());

                /**
                 * 进行消息分类
                 */
                if(rmg.getMessagePackage().equals("3")) {//文字信息
                    //使用工具类updatalist进行更新listview控件的文字信息
                    UpdateList.updateListText(listView, 3, rmg.getConText(), rmg.getDate(), rmg.getSender(), rmg.getGolder());

                    if(!rmg.getSender().equals(sender)){
                        //打开指定路径下的目录进行存储聊天记录
                        File file = new File("D:\\idea\\QQ\\libr\\ChatLog"+rmg.getGolder());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        //创建谁和谁的聊天文件
                        File fileLog = new File("D:\\idea\\QQ\\libr\\ChatLog"+rmg.getGolder()+"\\Log"+rmg.getGolder()+"to"+rmg.getSender()+".text");
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileLog,true)));//进行文件的追加写入
                        bw.write(rmg.getSender()+" "+rmg.getGolder());//写入发送者和接收者
                        bw.newLine();
                        bw.write(rmg.getDate());//日期
                        bw.newLine();
                        bw.write(rmg.getConText());//文字内容
                        bw.newLine();
                        bw.flush();
                    }
                }else if(rmg.getMessagePackage().equals("4")){//图片信息

                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\Imagefile"+rmg.getSender());
                        System.out.println(file.exists());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File fileImage = new File("D:\\idea\\QQ\\libr\\Imagefile"+rmg.getSender()+"\\"+rmg.getArray()+rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileImage);
                        fos.write(rmg.getFile());
                        UpdateList.updateListImage(listView,4,new ImageView("file:D:\\idea\\QQ\\libr\\Imagefile"+rmg.getSender()+"\\"+rmg.getArray()+rmg.getFileType()),rmg.getDate(),rmg.getSender(),rmg.getGolder());
                    }else {
                        File file = new File("D:\\idea\\QQ\\libr\\Imagefile" + rmg.getGolder());
                        System.out.println(file.exists());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File fileImage = new File("D:\\idea\\QQ\\libr\\Imagefile" + rmg.getGolder() + "\\" + rmg.getArray() + rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileImage);
                        fos.write(rmg.getFile());
                        UpdateList.updateListImage(listView, 4, new ImageView("file:D:\\idea\\QQ\\libr\\Imagefile" + rmg.getGolder() + "\\" + rmg.getArray() + rmg.getFileType()), rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }
                }else if(rmg.getMessagePackage().equals("5")){
                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\File"+rmg.getSender());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File allFile = new File("D:\\idea\\QQ\\libr\\File"+rmg.getSender()+"\\"+rmg.getFileName());
                        FileOutputStream fos = new FileOutputStream(allFile);
                        fos.write(rmg.getFile());
                        UpdateList.updateListText(listView, 5, "你发了一份文件，请到默认文件夹查看", rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }else {
                        File file = new File("D:\\idea\\QQ\\libr\\File" + rmg.getGolder());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File allFile = new File("D:\\idea\\QQ\\libr\\File" + rmg.getGolder() + "\\" + rmg.getFileName());
                        FileOutputStream fos = new FileOutputStream(allFile);
                        fos.write(rmg.getFile());
                        UpdateList.updateListText(listView, 5, "对方给你发了一份文件，请到默认文件夹查看", rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }
                }else if(rmg.getMessagePackage().equals("6")){
                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File fileAudio = new File("D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender()+"\\"+rmg.getFileName()+rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileAudio);
                        System.out.println("写入的文件数组字节大小为："+rmg.getFile().length);
                        fos.write(rmg.getFile());
                        System.out.println("D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender()+"\\"+rmg.getFileName()+rmg.getFileType());
                        UpdateList.updateListAudio(listView, 6, "播放","file:D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender()+"\\"+rmg.getFileName()+rmg.getFileType(), rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }else {
                        File file = new File("D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File fileAudio = new File("D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder() + "\\" + rmg.getFileName() + rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileAudio);
                        System.out.println("写入的文件数组字节大小为：" + rmg.getFile().length);
                        fos.write(rmg.getFile());
                        System.out.println("D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder() + "\\" + rmg.getFileName() + rmg.getFileType());
                        UpdateList.updateListAudio(listView, 6, "播放", "file:D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder() + "\\" + rmg.getFileName() + rmg.getFileType(), rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }
                }else if(rmg.getMessagePackage().equals("7")){//群聊列表头像

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                String publicList = rmg.getPublicList();
                                String[] s = publicList.split("_");
                                for (int i = 0; i <s.length && !s[i].equals(""); i++) {
                                    File file = new File("D:\\idea\\QQ\\libr\\head\\publicHead");
                                    if(!file.exists()){
                                        file.mkdirs();
                                    }
                                    File allFile = new File("D:\\idea\\QQ\\libr\\head\\publicHead\\"+s[i]+".jpg");
                                    byte[] s1 = rmg.getImage(s[i]);//从传过来的数据包中获取图像文件
                                    try {
                                        FileOutputStream out = new FileOutputStream(allFile);
                                        out.write(s1);//将头像文件写入到本地磁盘
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    publicChatList.getItems().add(new ListData(s[i],new ImageView("file:D:\\idea\\QQ\\libr\\head\\publicHead\\"+s[i]+'.'+"jpg")));
                            }
                        }
                    });
                }else if(rmg.getMessagePackage().equals("8")){
                    System.out.println("是否创建成功群聊"+rmg.getSuccess());
                    if(rmg.getSuccess()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                File file = new File("D:\\idea\\QQ\\libr\\head\\publicHead\\"+rmg.getConText()+".jpg");
                                FileOutputStream fos = null;
                                try {
                                    fos = new FileOutputStream(file);
                                    fos.write(rmg.getImage(rmg.getSender()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                ListData listData = new ListData(rmg.getConText(), new ImageView("file:D:\\idea\\QQ\\libr\\head\\publicHead\\" + rmg.getConText() + ".jpg"));
                                publicChatList.getItems().addAll(listData);
                                AlertScreen.getMessageScreen("创建群聊","创建成功，群号码为："+rmg.getConText());
                            }
                        });

                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                AlertScreen.getMessageScreen("创建群聊","创建失败");
                            }
                        });
                    }
                }else if(rmg.getMessagePackage().equals("9")){
                    File file = new File("D:\\idea\\QQ\\libr\\head\\publicHead"+rmg.getConText()+".jpg");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(rmg.getImage(rmg.getConText()));
                    ListData listData = new ListData(rmg.getConText(), new ImageView("file:D:\\idea\\QQ\\libr\\head\\publicHead" + rmg.getConText() + ".jpg"));

                    publicChatList.getItems().addAll(listData);
                }else if(rmg.getMessagePackage().equals("10")){
                    System.out.println("客户机接收到信息："+rmg.getConText());
                    UpdateList.updateListText(publicChat, 3, rmg.getConText(), rmg.getDate(), rmg.getSender(),rmg.getGolder());


                    if(!rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\ChatLog"+rmg.getGolder());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File fileLog = new File("D:\\idea\\QQ\\libr\\ChatLog"+rmg.getGolder()+"\\LogPublic"+rmg.getGolder()+"to"+rmg.getQqPublicNumber()+".text");
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileLog,true)));
                        bw.write(rmg.getSender()+" "+rmg.getGolder());
                        bw.newLine();
                        bw.write(rmg.getDate());
                        bw.newLine();
                        bw.write(rmg.getConText());
                        bw.newLine();
                        bw.flush();
                    }
                }else if(rmg.getMessagePackage().equals("11")){
                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\Imagefile"+rmg.getSender());
                        System.out.println(file.exists());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File fileImage = new File("D:\\idea\\QQ\\libr\\Imagefile"+rmg.getSender()+"\\"+rmg.getArray()+rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileImage);
                        fos.write(rmg.getFile());
                        UpdateList.updateListImage(publicChat,4,new ImageView("file:D:\\idea\\QQ\\libr\\Imagefile"+rmg.getSender()+"\\"+rmg.getArray()+rmg.getFileType()),rmg.getDate(),rmg.getSender(),rmg.getGolder());
                    }else {
                        File file = new File("D:\\idea\\QQ\\libr\\Imagefile" + rmg.getGolder());
                        System.out.println(file.exists());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File fileImage = new File("D:\\idea\\QQ\\libr\\Imagefile" + rmg.getGolder() + "\\" + rmg.getArray() + rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileImage);
                        fos.write(rmg.getFile());
                        UpdateList.updateListImage(publicChat, 4, new ImageView("file:D:\\idea\\QQ\\libr\\Imagefile"+rmg.getGolder() + "\\" + rmg.getArray() + rmg.getFileType()), rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }
                }else if(rmg.getMessagePackage().equals("12")){
                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\File"+rmg.getSender());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File allFile = new File("D:\\idea\\QQ\\libr\\File"+rmg.getSender()+"\\"+rmg.getFileName());
                        FileOutputStream fos = new FileOutputStream(allFile);
                        fos.write(rmg.getFile());
                        UpdateList.updateListText(publicChat, 5, "你发了一份文件，请到默认文件夹查看", rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }else {
                        File file = new File("D:\\idea\\QQ\\libr\\File" + rmg.getGolder());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File allFile = new File("D:\\idea\\QQ\\libr\\File" + rmg.getGolder() + "\\" + rmg.getFileName());
                        FileOutputStream fos = new FileOutputStream(allFile);
                        fos.write(rmg.getFile());
                        UpdateList.updateListText(publicChat, 5, "对方给你发了一份文件，请到默认文件夹查看", rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }
                }else if(rmg.getMessagePackage().equals("13")){
                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender());
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        File fileAudio = new File("D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender()+"\\"+rmg.getFileName()+rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileAudio);
                        System.out.println("写入的文件数组字节大小为："+rmg.getFile().length);
                        fos.write(rmg.getFile());
                        System.out.println("D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender()+"\\"+rmg.getFileName()+rmg.getFileType());
                        UpdateList.updateListAudio(publicChat, 6, "播放","file:D:\\idea\\QQ\\libr\\AudioFile"+rmg.getSender()+"\\"+rmg.getFileName()+rmg.getFileType(), rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }else {
                        File file = new File("D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File fileAudio = new File("D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder() + "\\" + rmg.getFileName() + rmg.getFileType());
                        FileOutputStream fos = new FileOutputStream(fileAudio);
                        System.out.println("写入的文件数组字节大小为：" + rmg.getFile().length);
                        fos.write(rmg.getFile());
                        System.out.println("D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder() + "\\" + rmg.getFileName() + rmg.getFileType());
                        UpdateList.updateListAudio(publicChat, 6, "播放", "file:D:\\idea\\QQ\\libr\\AudioFile" + rmg.getGolder() + "\\" + rmg.getFileName() + rmg.getFileType(), rmg.getDate(), rmg.getSender(), rmg.getGolder());
                    }
                }else if(rmg.getMessagePackage().equals("16")){
                    if(rmg.getSender().equals(sender)){
                        File file = new File("D:\\idea\\QQ\\libr\\head\\"+rmg.getConText()+".jpg");
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(rmg.getImage(rmg.getConText()));

                        friendList.getItems().addAll(new ListData(rmg.getConText(),new ImageView("file:D:\\idea\\QQ\\libr\\head\\"+rmg.getConText()+".jpg")));
                    }else{
                        File file = new File("D:\\idea\\QQ\\libr\\head\\"+rmg.getSender()+".jpg");
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(rmg.getImage(rmg.getSender()));

                        friendList.getItems().addAll(new ListData(rmg.getSender(),new ImageView("file:D:\\idea\\QQ\\libr\\head\\"+rmg.getSender()+".jpg")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
