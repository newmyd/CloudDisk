package file;

import database.dao.FileDao;

import java.util.ArrayList;

public class File {
    private int id = 0;
    private int dir = 0;
    private long siz = 0;
    private String name = null;
    private int user = 0;
    private String time = null;

    public int getId() {
        return id;
    }

    public int getDir() {
        return dir;
    }

    public String getName() {
        return name;
    }

    public int getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public long getSiz() {
        return siz;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSiz(long siz) {
        this.siz = siz;
    }

    public static File getFileById(int id) {
        File file;
        try {
            file = FileDao.getFileById(id);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean getFileByDir(int dir) {
        try {
            return FileDao.getFileByDir(dir);
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean addFile(File file) {
        try {
            FileDao.addFile(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean delFile(int id) {
        try {
            FileDao.delFile(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean setFile(int id, String name) {
        if (name == null || name.equals(""))
            return false;
        try {
            FileDao.setFile(id, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static ArrayList<File> getFileByUser(int userId, String name, String sot, int page, int siz) {
        ArrayList<File> res;
        try {
            res = FileDao.getFileByUser(userId, name, sot, page, siz);
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static int getFileSize(int userId, String name) {
        int res;
        try {
            res = FileDao.getFileSize(userId, name);
        } catch (Exception e) {
            return 0;
        }
        return res;
    }
}
