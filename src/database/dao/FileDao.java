package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import database.util.DatabaseConnect;

import file.File;
import user.User;

public class FileDao {

    public static int getSize() throws SQLException {
        int ans = 0;
        String str = "SELECT max(dir) FROM file";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        ResultSet res = sql.executeQuery();
        while (res.next())
            ans = res.getInt("max(dir)");
//        con.close();
        return ans;
    }

    public static void addFile(File file) throws SQLException {
        String str = "INSERT INTO file (dir, user, name, time, siz) VALUES (?, ?, ?, ?, ?)";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, file.getDir());
        sql.setInt(2, file.getUser());
        sql.setString(3, file.getName());
        sql.setString(4, file.getTime());
        sql.setLong(5, file.getSiz());
        sql.execute();
//        con.close();
        return ;
    }

    public static void delFile(int id) throws SQLException {
        String str = "DELETE FROM file WHERE id = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, id);
        sql.execute();
//        con.close();
        return ;
    }

    public static void setFile(int id, String name) throws SQLException {

        String str = "UPDATE file SET name = ? WHERE (id = ?)";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setString(1, name);
        sql.setInt(2, id);
        sql.execute();
//        con.close();
        return ;
    }

    public static boolean getFileByDir(int dir) throws SQLException{
        File file = new File();
        String str = "SELECT id FROM file where dir = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, dir);
        ResultSet res = sql.executeQuery();
        return res.next();
    }

    public static File getFileById(int id) throws SQLException{
        File file = new File();
        String str = "SELECT id, dir, user, name, time, siz FROM file where id = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, id);
        ResultSet res = sql.executeQuery();
        while (res.next()) {
            file.setId(res.getInt("id"));
            file.setDir(res.getInt("dir"));
            file.setUser(res.getInt("user"));
            file.setName(res.getString("name"));
            file.setTime(res.getString("time"));
            file.setSiz(res.getLong("siz"));
        }
//        con.close();
        return file;
    }

    public static ArrayList<File> getFileByUser(int user, String name, String sot, int page, int siz) throws SQLException {
        String str = "SELECT id, dir, name, time, siz FROM file where user = ? AND name LIKE ? ORDER BY " + sot + " limit ?, ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, user);
        sql.setString(2, name);
        sql.setInt(3, page);
        sql.setInt(4, siz);
        ResultSet res = sql.executeQuery();
        ArrayList<File> ans = new ArrayList<File>();

        while (res.next()) {
            File file = new File();
            file.setId(res.getInt("id"));
            file.setDir(res.getInt("dir"));
            file.setName(res.getString("name"));
            file.setTime(res.getString("time"));
            file.setSiz(res.getLong("siz"));
            ans.add(file);
        }
//        con.close();
        return ans;
    }

    public static int getFileSize(int user, String name) throws SQLException {
        String str = "SELECT count(*) FROM file where user = ? AND name LIKE ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, user);
        sql.setString(2, name);
        ResultSet res = sql.executeQuery();
        int ans = 0;
        while (res.next())
            ans = res.getInt("count(*)");
        return ans;
    }

    public static void delUser(int user) throws SQLException {

        String str = "DELETE FROM file WHERE user = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, user);
        sql.execute();
//        con.close();
        return ;
    }
}
