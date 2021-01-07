package client.manage;

import client.tools.AlertScreen;
import client.tools.ReturnMessagePackage;
import client.tools.User;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import static java.lang.System.out;

/**
 * 客户端的后台，连接服务器的地方
 * 方法：public boolean sendLoginInfo(User userinfo)；创建一个socket连接服务器，同时通过服务器返回的数据包
 * 确定是否账号密码合法，否合法，将客户端的socket加入HashMap进行管理，同时通过多线程进行阻塞式读取
 */
public class ClientConnectServer {

    public Socket client;
    public ReturnMessagePackage rsg;


    /**
     * 用于发送登录请求，并创建线程进行监听
     * @param userinfo
     * @return
     */
    public boolean sendLoginInfo(User userinfo){
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ReturnMessagePackage rmg = null;
        try{
            //读取配置文件
            Properties info = new Properties();
            info.load(getClass().getClassLoader().getResourceAsStream("info.properties"));

            client = new Socket(info.getProperty("host"),Integer.parseInt(info.getProperty("port")));//目标地址和端口
            oos = new ObjectOutputStream(client.getOutputStream());//通过socket获取对象输出管道ObjectOutputStream，
            ReturnMessagePackage sendRmg = new ReturnMessagePackage();//信息包ReturnMessagePackage
            sendRmg.setMessagePackage("1");//设置信息包为登录信息1
            oos.writeObject(sendRmg);//将信息包对象写入管道
            oos.writeObject(userinfo);//将用户包写入管道

            ois = new ObjectInputStream(client.getInputStream());//通过socket获取对象输入管道ObjectInputStream
            rmg = (ReturnMessagePackage)ois.readObject();//读取一个对象ReturnMessagePackage
        }catch (Exception e){
            out.println("客户端连接失败");
            e.printStackTrace();
            release(oos,ois);
        }
        if(rmg.getMessagePackage().equals("20")){//判断信息包的类型是否成功（20）
            //创建客户机的线程
            ClientThread ct = new ClientThread(client);
            new Thread(ct).start();
            //加入hashmap进行线程管理
            ManageClientThreadHash.addThread(userinfo.getAccount(),ct);
            rsg = rmg;
            return true;
        }else if(rmg.getMessagePackage().equals("21")){//密码错误
            AlertScreen.getMessageScreen("警告","密码错误,请重新输入");
            return false;
        }else if(rmg.getMessagePackage().equals("22")){//账号不存在
            AlertScreen.getMessageScreen("警告","账号不存在,请先注册");
            return false;
        }
        return false;

    }

    /**
     * 发送注册请求，
     * @param userinfo
     * @return
     */
    public boolean sendRegInfo(User userinfo){
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ReturnMessagePackage rmg = null;
        try{
            client = new Socket("localhost",9999);//目标地址和端口
            oos = new ObjectOutputStream(client.getOutputStream());
            ReturnMessagePackage sendRmg = new ReturnMessagePackage();
            sendRmg.setMessagePackage("2");//设置信息包为注册信息
            oos.writeObject(sendRmg);
            oos.writeObject(userinfo);
            ois = new ObjectInputStream(client.getInputStream());
            rmg = (ReturnMessagePackage)ois.readObject();
        }catch (Exception e){
            out.println("客户端连接失败");
            e.printStackTrace();
            release(oos,ois);
        }
        if(rmg.getMessagePackage().equals("24")){//注册成功信息24
            this.rsg = rmg;
            AlertScreen.getMessageScreen("系统消息","你的账号为:"+rmg.getSender()+" 你的密码为:"+userinfo.getPassword());
            return true;
        }
        else return false;
    }



    public void release(Closeable... items){
        try{
            for(Closeable item:items){
                item.close();
            }
        }catch (Exception e){
            out.println("流关闭出错");
            e.printStackTrace();
        }
    }
}
