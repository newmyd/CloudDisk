package database.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import user.User;

import database.util.DatabaseConnect;



public class UserDao {
    
    public static int getSize() throws SQLException {
        int ans = 0;
        String str = "SELECT max(userId) FROM user";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        ResultSet res = sql.executeQuery();
        while (res.next())
        	ans = res.getInt("max(userId)");
//        con.close();
        return ans;
    }
    
	public static User registerUser(User user) throws SQLException {
		String str = "INSERT INTO user (userName, passWord) VALUES (?, ?)";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setString(1, user.getUserName());
        sql.setString(2, user.getPassWord());
        sql.execute();
//        con.close();
		return user;
	}
	
    public static User getUserByName(String userName) throws SQLException {
        String str = "SELECT userId, userName, passWord FROM user where userName = ?";
        Connection con = DatabaseConnect.getCon();
		PreparedStatement sql = con.prepareStatement(str);
        sql.setString(1, userName);
        ResultSet res = sql.executeQuery();
        User user = new User();
        while (res.next()) {
        	user.setUserId(res.getInt("userId"));
        	user.setUserName(res.getString("userName"));
        	user.setPassWord(res.getString("passWord"));
        }
//        con.close();
        return user;
    }
	
    public static User getUserById(int userId) throws SQLException {
        String str = "SELECT userId, userName, passWord FROM user where userId = ?";
        Connection con = DatabaseConnect.getCon();
		PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, userId);
        ResultSet res = sql.executeQuery();
        User user = new User();
        while (res.next()) {
        	user.setUserId(res.getInt("userId"));
        	user.setUserName(res.getString("userName"));
        	user.setPassWord(res.getString("passWord"));
        }
//        con.close();
        return user;
    }

    public static User setUserName(User user) throws SQLException {
    	String str = "UPDATE user SET userName = ? WHERE (userId = ?)";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setString(1, user.getUserName());
        sql.setInt(2, user.getUserId());
        sql.execute();
//        con.close();
        return user;
    }

    public static User setPassWord(User user) throws SQLException {
    	String str = "UPDATE user SET passWord = ? WHERE (userId = ?)";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setString(1, user.getPassWord());
        sql.setInt(2, user.getUserId());
        sql.execute();
//        con.close();
        return user;
    }

    public static void delUser(int user) throws SQLException {

        String str = "DELETE FROM user WHERE userId = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, user);
        sql.execute();
//        con.close();
    }
}