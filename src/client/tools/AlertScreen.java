package client.tools;

import javafx.scene.control.Alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 工具类：用于弹出提示框
 */
public class AlertScreen implements ActionListener {
    public static Alert getMessageScreen(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.show();
        return alert;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("a");
    }
}
