package server;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetMethod {
    static String url = "jdbc:mysql://localhost:3306/register?serverTimezone=UTC";
    static String user = "root";
    static String passWord = "rootlzb";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,user,passWord);
        return con;
    }
}
