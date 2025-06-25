package disk;

import java.io.File;

public class Disk {


    public static boolean delete(String path) {

        File file = new File(path);
        if (!file.exists())
            return false;
        if (file.isFile())
            return file.delete();
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files)
                if (f.isFile())
                    if (!f.delete())
                        return false;
        }
        return file.delete();
    }
}
