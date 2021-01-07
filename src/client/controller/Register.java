package client.controller;

import client.manage.CheckUserInfo;
import client.tools.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * fxml生成的注册窗口的控制器，用于获取注册信息并将信息存入数据库，返回注册账号
 */
public class Register {

    @FXML
    private AnchorPane register;
    @FXML
    private Button NowReg;

    @FXML
    private TextField Phone;

    @FXML
    private TextField Birthday;

    @FXML
    private TextField Name;

    @FXML
    private TextField Password;


    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    void now_reg(MouseEvent event) throws Exception {


        User user = new User();
        user.setAccount(Name.getText());
        user.setPassword(Password.getText());
        user.setPhone(Phone.getText());
        if(new CheckUserInfo().check1(user)){
            Stage s = (Stage)register.getScene().getWindow();
            s.close();
        }
    }
}

