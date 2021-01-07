package server;

import java.util.HashMap;

public class ManageFriendList {
    public static HashMap<String,String > hashMap = new HashMap<>();
    public static void addFriendList(String publicAc,String friendList){
        hashMap.put(publicAc,friendList);
    }
    public static String getFriendList(String publicAc){
        return hashMap.get(publicAc);
    }
    public static void replace(String publicAc,String newList){
        hashMap.remove(publicAc);
        hashMap.put(publicAc,newList);
    }
}
