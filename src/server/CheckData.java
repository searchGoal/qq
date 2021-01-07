package server;

import client.tools.ReturnMessagePackage;
import client.tools.User;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class CheckData {

    private int flag=0;

    public int check(User userInfo, ReturnMessagePackage rmg){
        try {
            String sql = "select * from user_info where User=?";
            Connection con = GetMethod.getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1,userInfo.getAccount());
            ResultSet rs1 = pre.executeQuery();
            if(rmg.getMessagePackage().equals("1")){
                while(rs1.next()){
                    if(rs1.getString(4).equals(userInfo.getAccount())){
                        if(rs1.getString(5).equals(userInfo.getPassword())){
                            flag = 1;//登录成功
                            String sqlFriend = "select * from friend_list where Account = ?";//获取好友头像头像信息
                            PreparedStatement pre1 = con.prepareStatement(sqlFriend);
                            pre1.setString(1,userInfo.getAccount());
                            ResultSet rs2 = pre1.executeQuery();
                            rs2.first();
                            String[] s = rs2.getString(2).split("_");
                            for (int i = 0 ; i < s.length &&!s[i].equals(""); i++) {
                                PreparedStatement pre2 = con.prepareStatement(sql);
                                pre2.setString(1,s[i]);
                                ResultSet rs3 = pre2.executeQuery();
                                rs3.next();
                                Blob blob = rs3.getBlob(6);
                                byte[] b= blob.getBytes(1L,(int)blob.length());
                                rmg.addImage(s[i],b);
                            }
                            PreparedStatement pre3 = con.prepareStatement(sql);//获取自己头像信息
                            pre3.setString(1,userInfo.getAccount());
                            ResultSet rs4 = pre3.executeQuery();
                            rs4.next();
                            Blob blob = rs4.getBlob(6);
                            byte[] bytes = blob.getBytes(1L, (int) blob.length());
                            System.out.println("读取到自己的头像数据"+bytes.length);
                            rmg.addImage(userInfo.getAccount(),bytes);
                            rmg.setFriendList(rs2.getString(2));
                            rmg.setName(rs4.getString(1));
                            rmg.setSign(rs4.getString(7));
                            break;
                        }else{
                            flag = 2;//密码错误
                            break;
                        }
                    }
                }
                if(flag == 0)flag=3;//账号不存在
            } else if(rmg.getMessagePackage().equals("2")){
                while(true){
                    Random rand = new Random();
                    int account = rand.nextInt(10000);
                    while(rs1.next()){
                        if(rs1.getString(4).equals(account+"")){
                            flag=4;//账号已经存在
                        }
                    }
                    if(flag==0){
                        String sqlInsert = "insert into user_info (Name,Address,Phone,User,Password,Picture) values (?,?,?,?,?,?)";
                        PreparedStatement preInsert = con.prepareStatement(sqlInsert);
                        preInsert.setString(1,userInfo.getAccount());
                        preInsert.setString(2,userInfo.getAddress());
                        preInsert.setString(3,userInfo.getPhone());
                        preInsert.setString(4,account+"");
                        preInsert.setString(5,userInfo.getPassword());

                        //设置默认头像
                        File file = new File("D:\\idea\\QQ\\libr\\QQimage.jpg");
                        FileInputStream fs = new FileInputStream(file);
                        preInsert.setBinaryStream(6,fs,file.length());
                        System.out.println("文件长度"+file.length());


                        preInsert.execute();
                        String sqlInsertFriend = "insert into friend_list (Account,FriendList) values (?,'') ";
                        PreparedStatement preInsertFriend = con.prepareStatement(sqlInsertFriend);
                        preInsertFriend.setString(1,account+"");
                        preInsertFriend.execute();
                        System.out.println("在服务器注册的账号为:"+account);
                        rmg.setSender(account+"");
                        break;
                    }
                }
            }
            rs1.close();
            pre.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public void check1(ReturnMessagePackage rmg) throws Exception {
        String sqlQuerychat = "select * from public_chat where Id= ? ";
        String sqlQueryaccount = "select * from public_account where PublicAc = ?";
        String sqlInsertaccount = "insert into public_account (PublicAc,Member,Head) values (?,?,?)";
        String sqlInsertChat = "insert into public_chat (Id,PublicFriend) values (?,?)";
        String sqlUpdata = "update public_account set Member = ? where PublicAc = ?";
        Connection con = GetMethod.getConnection();
        if(rmg.getMessagePackage().equals("7")){//查询当前账户的群聊列表
            PreparedStatement prePublic = con.prepareStatement(sqlQuerychat);
            prePublic.setString(1,rmg.getSender());

            ResultSet rsPublic = prePublic.executeQuery();
            StringBuffer publicList = new StringBuffer();
            while(rsPublic.next()){
                publicList.append(rsPublic.getString(2)+"_");
                System.out.println(rsPublic.getString(2));
            }
            String[] s = publicList.toString().split("_");
            PreparedStatement preQuery = con.prepareStatement(sqlQueryaccount);
            for (int i = 0; i<s.length&&!s[i].equals("") ; i++) {
                preQuery.setString(1,s[i]);
                ResultSet rs = preQuery.executeQuery();
                rs.next();
                ManageFriendList.addFriendList(s[i],rs.getString(2));
                Blob blob = rs.getBlob(3);
                rmg.addImage(s[i],blob.getBytes(1L,(int)blob.length()));
            }
            rmg.setPublicList(publicList.toString());
            System.out.println("返回的群聊列表为"+publicList);
        }else if(rmg.getMessagePackage().equals("8")){//创建群号
            PreparedStatement prePublic = con.prepareStatement("select * from public_account");//"select * from public_chat where Id= ? "
            ResultSet rs = prePublic.executeQuery();
            String ac = "";
            while (true){
                int flag =0;
                Random random = new Random();
                ac = 100000+random.nextInt(10000)+"";
                while(rs.next()){
                    if(rs.getString(1).equals("ac")){
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0)break;
            }
            PreparedStatement preInsert = con.prepareStatement(sqlInsertaccount);//"insert into public_account (PublicAc,Member,Head) values (?,?,?)"
            preInsert.setString(1,ac);
            System.out.println("新创建的群号为"+ac);
            preInsert.setString(2,rmg.getSender()+"_");

            File file = new File("D:\\idea\\QQ\\libr\\QQimage.jpg");//默认群头像
            FileInputStream fis = new FileInputStream(file);
            preInsert.setBinaryStream(3,fis,file.length());
            int i = preInsert.executeUpdate();
            fis.close();
            if (ac != "") {
                rmg.setSuccess(true);
                rmg.setConText(ac);
            }else{
                rmg.setSuccess(false);
            }

            PreparedStatement prechat = con.prepareStatement(sqlInsertChat);
            prechat.setString(1,rmg.getSender());
            prechat.setString(2,ac);
            prechat.execute();

            File fileImage = new File("D:\\idea\\QQ\\libr\\QQimage.jpg");
            FileInputStream fisImage = new FileInputStream(fileImage);
            byte[] bytes = new byte[(int)fileImage.length()];
            fisImage.read(bytes);
            rmg.addImage(rmg.getSender(),bytes);
        }else if(rmg.getMessagePackage().equals("9")){//加入群聊
            PreparedStatement pre = con.prepareStatement(sqlInsertChat);
            pre.setString(1,rmg.getSender());
            pre.setString(2,rmg.getConText());
            pre.execute();
            PreparedStatement pre1 = con.prepareStatement(sqlQueryaccount);
            pre1.setString(1,rmg.getConText());
            ResultSet rs = pre1.executeQuery();
            rs.next();
            String s = rs.getString(2);
            Blob blob = rs.getBlob(3);
            rmg.addImage(rmg.getConText(),blob.getBytes(1L,(int)blob.length()));

            StringBuffer buffer = new StringBuffer(s);
            buffer.append(rmg.getSender()+"_");
            ManageFriendList.replace(rmg.getConText(),buffer.toString());
            PreparedStatement pre2 = con.prepareStatement(sqlUpdata);
            pre2.setString(1,buffer.toString());
            pre2.setString(2,rmg.getConText());
            pre2.execute();

            rmg.setSuccess(true);
        }else if(rmg.getMessagePackage().equals("14")){
            String sql = "update user_info set Name = ? where User = ?";
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1,rmg.getConText());
            pre.setString(2,rmg.getSender());
            pre.executeUpdate();
        }else if(rmg.getMessagePackage().equals("15")){
            String sql = "update user_info set Sign = ? where User = ?";
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1,rmg.getConText());
            pre.setString(2,rmg.getSender());
            pre.executeUpdate();
        }else if(rmg.getMessagePackage().equals("16")){
            String sql = "select * from friend_list where Account = ?";
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1,rmg.getSender());
            ResultSet rs = pre.executeQuery();
            rs.next();
            String friend = rs.getString(2);
            friend = friend +rmg.getConText()+"_";

            pre.setString(1,rmg.getConText());
            ResultSet rsFriend = pre.executeQuery();
            rsFriend.next();
            String friend1 = rsFriend.getString(2);
            friend1 = friend1+rmg.getSender()+"_";

            String sqlUpdate = "update friend_list set FriendList = ? where Account = ?";
            PreparedStatement preUpdate = con.prepareStatement(sqlUpdate);
            preUpdate.setString(1,friend);
            preUpdate.setString(2,rmg.getSender());
            preUpdate.execute();

            preUpdate.setString(1,friend1);
            preUpdate.setString(2,rmg.getConText());
            preUpdate.execute();

            String sqlQuery = "select * from user_info where User = ?";
            PreparedStatement preQuery = con.prepareStatement(sqlQuery);
            preQuery.setString(1,rmg.getConText());
            ResultSet rsQuery = preQuery.executeQuery();
            rsQuery.next();
            Blob blob = rsQuery.getBlob(6);
            rmg.addImage(rmg.getConText(),blob.getBytes(1L,(int)blob.length()));


            preQuery.setString(1,rmg.getSender());
            ResultSet rsQuery1 = preQuery.executeQuery();
            rsQuery1.next();
            Blob blob1 = rsQuery1.getBlob(6);
            rmg.addImage(rmg.getSender(),blob1.getBytes(1L,(int)blob1.length()));
            rmg.setSuccess(true);
        }
        con.close();
    }
}
