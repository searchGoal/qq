package client.tools;

import java.sql.*;

/**
 * 工具类：
 * check();        用于检测账号是否在数据库中存在，返回false没有重复名字，true有重复名字
 * checkpass();     用于检测账号和密码是否匹配，返回false代表账号密码不匹配或者账号不存在，true代表账号密码匹配
 */

public class CheckUser {
    static String url = "jdbc:mysql://localhost:3306/register?serverTimezone=UTC";
    static String user = "root";
    static  String pass = "rootlzb";
    static String sql = "select * from user_info";
    static Connection con = null;
    static PreparedStatement pre = null;
    static ResultSet rs = null;


    public static boolean check(int User_id){
        boolean flag =false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while(rs.next()){
                int account = rs.getInt(4);
                if(account == User_id){
                    flag = true;
                }
            }
            pre.close();
            con.close();
        }catch(Exception e){
            System.out.println("数据查询异常");
            e.printStackTrace();
        }
        if(flag == false)return false;//返回false没有重复名字，true有重复名字
        else return true;
    }

    public static boolean checkpass(int account, String pass){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,CheckUser.pass);
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while(rs.next()) {
                if (rs.getInt(4) == account){
                    if(rs.getString(5).equals(pass))
                        return true;
                }
            }
            pre.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
