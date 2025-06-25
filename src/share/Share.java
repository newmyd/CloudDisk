package share;

import database.dao.ShareDao;
import java.util.ArrayList;

public class Share {
    private int id = 0;
    private int dir = 0;
    private long siz = 0;
    private String name = null;
    private int user = 0;
    private String time = null;
    private int src = 0;

    public int getId() {
        return id;
    }

    public int getDir() {
        return dir;
    }

    public long getSiz() {
        return siz;
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

    public int getSrc() {
        return src;
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

    public void setSrc(int src) {
        this.src = src;
    }

    public void setSiz(long siz) {
        this.siz = siz;
    }

    public static Share getFileById(int id) {
        Share file;
        try {
            file = ShareDao.getFileById(id);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean getFileByDir(int dir) {
        try {
            return ShareDao.getFileByDir(dir);
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean addFile(Share file) {
        try {
            ShareDao.addFile(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean delFile(int id) {
        try {
            ShareDao.delFile(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean setFile(int id, String name) {
        if (name == null || name.equals(""))
            return false;
        try {
            ShareDao.setFile(id, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<Share> getFileByUser(int user, String name, String sot, int page, int siz) {
        ArrayList<Share> res;
        try {
            res = ShareDao.getFileByUser(user, name, sot, page, siz);
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static ArrayList<Share> getFileBySrc(int src, String name, String sot, int page, int siz) {
        ArrayList<Share> res;
        try {
            res = ShareDao.getFileBySrc(src, name, sot, page, siz);
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static int getSizeByUser(int user, String name) {
        int res;
        try {
            res = ShareDao.getSizeByUser(user, name);
        } catch (Exception e) {
            return 0;
        }
        return res;
    }

    public static int getSizeBySrc(int src, String name) {
        int res;
        try {
            res = ShareDao.getSizeBySrc(src, name);
        } catch (Exception e) {
            return 0;
        }
        return res;
    }
}
