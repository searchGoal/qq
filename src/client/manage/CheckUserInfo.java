package client.manage;

import client.tools.User;

/**
 * model最底层，用于判断view层传上来的账号密码是否合法
 */
public class CheckUserInfo {
    public ClientConnectServer ct;
    public boolean check(User userInfo)  {//登录信息合法
         ct = new ClientConnectServer();
        if(ct.sendLoginInfo(userInfo)){
            return true;
        }else return false;
    }
    public boolean check1(User userInfo){//注册信息合法
        return new ClientConnectServer().sendRegInfo(userInfo);
    }
}
