package client.manage;

import java.util.HashMap;

/**
 * 管理类：用于保存客户端的多个socket
 */
public class ManageClientThreadHash {

    public static HashMap<String, ClientThread> hashMap = new HashMap<>();

    public static void addThread(String uid,ClientThread ct){
        ManageClientThreadHash.hashMap.put(uid,ct);
    }
    public static ClientThread getThread(String uid){
        return ManageClientThreadHash.hashMap.get(uid);
    }
}
