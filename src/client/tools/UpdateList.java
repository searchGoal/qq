package client.tools;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class UpdateList {
    public static void updateListText(ListView<ChatData> listView, int isFile,String mess,String data,String sender,String receiver){
        Platform.runLater(new Thread(new Runnable() {
            @Override
            public void run() {
                ChatData chatData = new ChatData(isFile,mess,data,sender,receiver);
                listView.getItems().add(chatData);
                listView.scrollTo(chatData);
                listView.refresh();
            }
        }));
    }
    public static void updateListImage(ListView<ChatData> listView, int isFile, ImageView imageView, String data, String sender, String receiver) {
        Platform.runLater(new Thread(new Runnable() {
            @Override
            public void run() {
                ChatData chatData = new ChatData(isFile,imageView, data, sender, receiver);
                listView.getItems().add(chatData);
                listView.scrollTo(chatData);
            }
        }));
    }
    public static void updateListAudio(ListView<ChatData> listView, int isFile, String mess,String fileName, String data, String sender, String receiver) {
        Platform.runLater(new Thread(new Runnable() {
            @Override
            public void run() {
                ChatData chatData = new ChatData(isFile,mess,fileName, data, sender, receiver);
                listView.getItems().add(chatData);
                listView.scrollTo(chatData);
            }
        }));
    }
}
