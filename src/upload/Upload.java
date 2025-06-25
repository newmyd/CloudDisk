package upload;

import database.dao.FileDao;

import java.util.HashMap;

public class Upload {
    private static int id = -1;
    private static final HashMap<Integer, Integer> mp = new HashMap<>();
    private static final HashMap<Integer, Long> mpsiz = new HashMap<>();

    private static int initId() {
        try {
            return id = FileDao.getSize();
        } catch (Exception e) {
            e.printStackTrace();
            return id = -1;
        }
    }

    public static Integer get(Integer x) {
        return mp.get(x);
    }

    public static void put(Integer x, Integer str) {
        mp.put(x, str);
    }

    public static void remove(Integer x) {
        mp.remove(x);
    }


    public static long getSiz(Integer x) {
        Long res = mpsiz.get(x);
        return res == null ? 0 : res;
    }

    public static void putSiz(Integer x, Long siz) {
        mpsiz.put(x, siz);
    }

    public static void addSiz(Integer x, Long siz) {
        Long sz = mpsiz.get(x);
        if (sz == null) {
            sz = 0L;
        }
        mpsiz.put(x, sz + siz);
    }

    public static void removeSiz(Integer x) {
        mpsiz.remove(x);
    }

    public static int getId() {
        if (id == -1)
            if (initId() == -1)
                return -1;
        return ++id;
    }
}
