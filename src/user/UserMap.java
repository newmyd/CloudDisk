package user;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class UserMap {
    private static final HashMap<Integer, HttpSession> mp = new HashMap<>();

    public static HttpSession get(Integer x) {
        return mp.get(x);
    }

    public static void put(Integer x, HttpSession y) {
        mp.put(x, y);
    }

    public static void remove(Integer x) {
        mp.remove(x);
    }
}
