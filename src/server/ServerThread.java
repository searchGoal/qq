package server;

import client.tools.ReturnMessagePackage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 服务器与客户端的线程
 */
public class ServerThread implements Runnable{
    public Socket s;
    public ObjectInputStream ois;
    public ServerThread(Socket s){
        this.s = s;
    }
    public void run() {
        while(true){
            try{
                ois = new ObjectInputStream(s.getInputStream());
                ReturnMessagePackage rmg = (ReturnMessagePackage)ois.readObject();

                System.out.println("收到的信息："+rmg.getConText()+"发送者："+rmg.getSender()+" 接收者："+rmg.getGolder());

                if (rmg.getMessagePackage().equals("3") || rmg.getMessagePackage().equals("4")||rmg.getMessagePackage().equals("5")||rmg.getMessagePackage().equals("6")){//接收到文字信息
                    ServerThread thread = ManageHash.getThread(rmg.getGolder());
                    if(thread == null){
                        System.out.println("私聊接收方当前没有窗口，暂停发送");
                        continue;
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                    oos.writeObject(rmg);

                    ServerThread thread1 = ManageHash.getThread(rmg.getSender());
                    if(thread1 == null){
                        System.out.println("私聊接收方当前没有窗口，暂停发送");
                        continue;
                    }
                    ObjectOutputStream oos1 = new ObjectOutputStream(thread1.s.getOutputStream());
                    oos1.writeObject(rmg);
                }else if(rmg.getMessagePackage().equals("7")){//群聊列表
                    new CheckData().check1(rmg);
                    ServerThread thread = ManageHash.getThread(rmg.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                    oos.writeObject(rmg);
                }else if(rmg.getMessagePackage().equals("8")){//创建群聊
                    new CheckData().check1(rmg);
                    ServerThread thread = ManageHash.getThread(rmg.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                    oos.writeObject(rmg);
                }else if(rmg.getMessagePackage().equals("9")){
                    new CheckData().check1(rmg);
                    ServerThread thread = ManageHash.getThread(rmg.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                    oos.writeObject(rmg);
                }else if(rmg.getMessagePackage().equals("10") ||rmg.getMessagePackage().equals("11")||rmg.getMessagePackage().equals("12")||rmg.getMessagePackage().equals("13")){
                    System.out.println("服务器收到群聊信息："+rmg.getConText());
                    String friendList = ManageFriendList.getFriendList(rmg.getGolder());
                    System.out.println("服务器获取到的群聊好友："+friendList);
                    rmg.setQqPublicNumber(rmg.getGolder());
                    String[] s = friendList.split("_");
                    for (int i = 0; i < s.length &&!s[i].equals("") ; i++) {
                        ServerThread thread = ManageHash.getThread(s[i]);
                        if(thread == null)continue;
                        Socket s1 = thread.s;
                        System.out.println("是否获取到了socket"+s[i]+"   "+(s1 != null));
                        rmg.setGolder(s[i]);
                        ObjectOutputStream oos = new ObjectOutputStream(s1.getOutputStream());
                        oos.writeObject(rmg);
                    }
                }else if(rmg.getMessagePackage().equals("14") ||rmg.getMessagePackage().equals("15")){
                    new CheckData().check1(rmg);
                }else if(rmg.getMessagePackage().equals("16")){
                    new CheckData().check1(rmg);
                    ServerThread thread = ManageHash.getThread(rmg.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                    oos.writeObject(rmg);

                    ServerThread thread1 = ManageHash.getThread(rmg.getConText());
                    ObjectOutputStream oos1 = new ObjectOutputStream(thread1.s.getOutputStream());
                    oos1.writeObject(rmg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
