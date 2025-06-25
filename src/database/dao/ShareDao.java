package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import database.util.DatabaseConnect;

import share.Share;

public class ShareDao {
    public static void addFile(Share file) throws SQLException {
        String str = "INSERT INTO share (dir, user, name, time, src, siz) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement sql = DatabaseConnect.getCon().prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, file.getDir());
        sql.setInt(2, file.getUser());
        sql.setString(3, file.getName());
        sql.setString(4, file.getTime());
        sql.setInt(5, file.getSrc());
        sql.setLong(6, file.getSiz());
        sql.execute();
//        con.close();
    }

    public static void delFile(int id) throws SQLException {
        String str = "DELETE FROM share WHERE id = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, id);
        sql.execute();
//        con.close();
    }

    public static void setFile(int id, String name) throws SQLException {

        String str = "UPDATE share SET name = ? WHERE (id = ?)";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setString(1, name);
        sql.setInt(2, id);
        sql.execute();
//        con.close();
    }

    public static boolean getFileByDir(int dir) throws SQLException{
        String str = "SELECT id FROM share where dir = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, dir);
        ResultSet res = sql.executeQuery();
        return res.next();
    }

    public static Share getFileById(int id) throws SQLException{
        Share file = new Share();
        String str = "SELECT id, dir, user, name, time, src, siz FROM share where id = ?";
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
            file.setSrc(res.getInt("src"));
            file.setSiz(res.getLong("siz"));
        }
//        con.close();
        return file;
    }

    public static ArrayList<Share> getFileByUser(int user, String name, String sot, int page, int siz) throws SQLException {
        String str = "SELECT id, dir, name, time, src, siz FROM share where user = ? AND name LIKE ? ORDER BY " + sot + " limit ?, ?";
        PreparedStatement sql = DatabaseConnect.getCon().prepareStatement(str);
        sql.setInt(1, user);
        sql.setString(2, name);
        sql.setInt(3, page);
        sql.setInt(4, siz);
        ResultSet res = sql.executeQuery();
        ArrayList<Share> ans = new ArrayList<>();

        while (res.next()) {
            Share file = new Share();
            file.setId(res.getInt("id"));
            file.setDir(res.getInt("dir"));
            file.setName(res.getString("name"));
            file.setTime(res.getString("time"));
            file.setSrc(res.getInt("src"));
            file.setSiz(res.getLong("siz"));
            ans.add(file);
        }
//        con.close();
        return ans;
    }

    public static ArrayList<Share> getFileBySrc(int src, String name, String sot, int page, int siz) throws SQLException {
        String str = "SELECT id, dir, user, name, time, siz FROM share where src = ? AND name LIKE ? ORDER BY " + sot + " limit ?, ?";
        PreparedStatement sql = DatabaseConnect.getCon().prepareStatement(str);
        sql.setInt(1, src);
        sql.setString(2, name);
        sql.setInt(3, page);
        sql.setInt(4, siz);
        ResultSet res = sql.executeQuery();
        ArrayList<Share> ans = new ArrayList<>();

        while (res.next()) {
            Share file = new Share();
            file.setId(res.getInt("id"));
            file.setDir(res.getInt("dir"));
            file.setName(res.getString("name"));
            file.setTime(res.getString("time"));
            file.setUser(res.getInt("user"));
            file.setSiz(res.getLong("siz"));
            ans.add(file);
        }
//        con.close();
        return ans;
    }

    public static int getSizeByUser(int user, String name) throws SQLException {
        String str = "SELECT count(*) FROM share where user = ? AND name LIKE ?";
        PreparedStatement sql = DatabaseConnect.getCon().prepareStatement(str);
        sql.setInt(1, user);
        sql.setString(2, name);
        ResultSet res = sql.executeQuery();
        int ans = 0;
        while (res.next())
            ans = res.getInt("count(*)");
        return ans;
    }

    public static int getSizeBySrc(int src, String name) throws SQLException {
        String str = "SELECT count(*) FROM share where src = ? AND name LIKE ?";
        PreparedStatement sql = DatabaseConnect.getCon().prepareStatement(str);
        sql.setInt(1, src);
        sql.setString(2, name);
        ResultSet res = sql.executeQuery();
        int ans = 0;
        while (res.next())
            ans = res.getInt("count(*)");
        return ans;
    }

    public static void delUser(int user) throws SQLException {

        String str = "DELETE FROM share WHERE user = ?";
        Connection con = DatabaseConnect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setInt(1, user);
        sql.execute();
//        con.close();
    }
}
