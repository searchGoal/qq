package client.tools;


import javafx.scene.image.ImageView;

public class ChatData {
    /**
     * isFile:
     *  3:文字信息
     *  4：图片信息
     *  5：文件信息
     *  6.语音文件
     */
    public int isFile;
    public ImageView image;
    public String mess;
    public String data;
    public String sender;
    public String receiver;
    public String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public ChatData(int isFile, ImageView image, String data,String sender,String receiver) {
        this.isFile = isFile;
        this.image = image;
        this.data = data;
        this.sender = sender;
        this.receiver = receiver;
    }
    public ChatData(int isFile, String mess,String fileName, String data,String sender,String receiver) {
        this.isFile = isFile;
        this.fileName =fileName;
        this.mess = mess;
        this.data = data;
        this.sender = sender;
        this.receiver = receiver;
    }
    public ChatData(int isFile, String mess, String data,String sender,String receiver) {
        this.isFile = isFile;
        this.mess = mess;
        this.data = data;
        this.sender = sender;
        this.receiver = receiver;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getisFile() {
        return isFile;
    }

    public void setisFile(int file) {
        isFile = file;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
