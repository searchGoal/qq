package client.manage;
/**
 * 管理新创建的stage，并将控制类Chat加入到管理池重
 */

import client.controller.Chat;

import java.util.HashMap;

public class ManageTextAreaMap {

    public static HashMap<String, Chat> hashMap = new HashMap<>();

    public static void addTextArea(String stageName,Chat chat){
        hashMap.put(stageName,chat);
    }

    public static Chat getChat(String stageName){
        return hashMap.get(stageName);
    }
}
