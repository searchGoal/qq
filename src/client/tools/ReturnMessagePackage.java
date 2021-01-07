package client.tools;
/**
 * 数据包类：都是发送和接收的数据，包括发送者id和接收者id和信息等
 * 1:发送登录请求检测信息包
 * 2:发送注册请求检测信息包
 * 3.发送私聊信息
 * 4.发送私聊图片
 * 5.发送私聊文件
 * 6.发送语音文件
 * 7.发送获得群聊名称和头像信息
 * 8.创建群聊请求
 * 9.添加群聊请求
 * 10.发送群聊信息
 * 11.发送群聊图片
 * 12.发送群聊文件
 * 13.发送群聊语音
 *
 * 14.更新昵称
 * 15.更新签名
 *
 *
 * 20:登录请求通过，允许登录
 * 21:登录请求不通过，密码错误
 * 22：登录请求不通过，账号不存在
 * 23:注册账号，注册成功
 */

import java.io.Serializable;
import java.util.HashMap;

public class ReturnMessagePackage implements Serializable {
    private String messagePackage;
    private String sender;
    private String golder;
    private String conText;
    private String date;
    private String friendList;
    private String publicList;
    private boolean Success;
    private String name;
    private String qqPublicNumber;



    private String sign;
    private byte[] file;
    private String fileType;//文件类型
    private String array;//根据文件名所生成的十六进制，用于识别不同文件
    private String fileName;//文件名

    HashMap<String,byte[]> imageHash = new HashMap<>();
    HashMap<String,String> nameHash = new HashMap<>();

    public String getQqPublicNumber() {
        return qqPublicNumber;
    }

    public void setQqPublicNumber(String qqPublicNumber) {
        this.qqPublicNumber = qqPublicNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getPublicList() {
        return publicList;
    }

    public void setPublicList(String publicList) {
        this.publicList = publicList;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] bytes) {
        this.file = bytes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }




    public void addName(String user,String name){
        nameHash.put(user,name);
    }
    public String getName(String user){
        return nameHash.get(user);
    }
    public void addImage(String name,byte[] image){
        imageHash.put(name,image);
    }
    public byte[] getImage(String name){
        return imageHash.get(name);
    }
    public String getFriendList() {
        return friendList;
    }

    public void setFriendList(String friendList) {
        this.friendList = friendList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGolder() {
        return golder;
    }

    public void setGolder(String golder) {
        this.golder = golder;
    }

    public String getConText() {
        return conText;
    }

    public void setConText(String conText) {
        this.conText = conText;
    }

    public String getMessagePackage() {
        return messagePackage;
    }

    public void setMessagePackage(String messagePackage) {
        this.messagePackage = messagePackage;
    }
}
