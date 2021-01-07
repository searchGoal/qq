package server;

import client.tools.ReturnMessagePackage;
import client.tools.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TrueServer {
    public TrueServer() throws  Exception{
        System.out.println("正在9999号端口监听");
        ServerSocket server = new ServerSocket(9999);
        while(true) {
            Socket serverAccept = server.accept();

            //验证账号密码是否正确
            ObjectInputStream ois = new ObjectInputStream(serverAccept.getInputStream());
            ReturnMessagePackage srmg = (ReturnMessagePackage)ois.readObject();
            ObjectOutputStream oos = new ObjectOutputStream(serverAccept.getOutputStream());

            if (srmg.getMessagePackage().equals("1")) {//登录包
                User rmg = (User) ois.readObject();
                System.out.println("服务器接收到的账号："+rmg.getAccount()+" 密码："+rmg.getPassword());
                int isSuccess = new CheckData().check(rmg,srmg);
                if(isSuccess == 1){
                    srmg.setMessagePackage("20");
                    oos.writeObject(srmg);

                    //成功登录加入线程
                    ServerThread s = new ServerThread(serverAccept);
                    new Thread(s).start();

                    //并且加入管理hashmap
                    ManageHash.addThread(rmg.getAccount(),s);

                }else if(isSuccess == 2){
                        srmg.setMessagePackage("21");
                        oos.writeObject(srmg);
                }else if(isSuccess == 3){
                    srmg.setMessagePackage("22");
                    oos.writeObject(srmg);
                }
            } else if(srmg.getMessagePackage().equals("2")){//注册包
                User rmg = (User) ois.readObject();
                System.out.println("注册昵称："+rmg.getAccount()+"  密码："+rmg.getPassword());
                int isSuccess = new CheckData().check(rmg,srmg);
                System.out.println(srmg.getSender());
                if(isSuccess == 0){
                    srmg.setMessagePackage("24");
                    oos.writeObject(srmg);
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new TrueServer();
    }

}
