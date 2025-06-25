package database.util;

import java.sql.*;

public class DatabaseConnect {
    private static Connection con;
    private static final int timeout = 1;
    private static final String URL = "jdbc:mysql://localhost:3306/clouddisk?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "mysql";
    private static final String PASSWORD = "mysql";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("connect database error");
            e.printStackTrace();
        }
        return con;
    }

    public static Connection getCon() {
        try {
            if (con == null)
                return connect();

            if (con.isClosed() || !con.isValid(timeout)) {
                con.close();
                connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}