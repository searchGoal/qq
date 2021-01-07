package server;

import java.util.HashMap;

/**
 * 线程管理，通过用户的id来管理对应的线程
 */
public class ManageHash {


    public static HashMap<String,ServerThread> hashMap = new HashMap<>();


    public static void addThread(String uid,ServerThread thread){
        hashMap.put(uid,thread);
        System.out.println("服务器添加了一个线程");
    }
    public static ServerThread getThread(String uid){
            return hashMap.get(uid);
    }
}
