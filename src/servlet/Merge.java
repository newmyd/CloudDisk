package servlet;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;

import javax.servlet.http.*;

import upload.Upload;


public class Merge extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String fileName = "0";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.getWriter().print("Login");
            return ;
        }

        int userId = (int) session.getAttribute("user");

        if (userId == 0) {
            response.getWriter().print("Login");
            return ;
        }


        int dir = Integer.parseInt(request.getParameter("dir"));
        int idx = Integer.parseInt(request.getParameter("idx"));
        String name = request.getParameter("name");

        if (userId != Upload.get(dir)) {
            return ;
        }


        String dirStr = request.getServletContext().getRealPath("./disk/") + dir + File.separator;

        if (idx <= 0) {
            try{
                File fil = new File(dirStr + fileName);
                new File(dirStr).mkdirs();
                if (!fil.exists())
                    fil.createNewFile();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            FileOutputStream out = new FileOutputStream(dirStr + fileName);

            for (int i = 1; i <= idx; ++i) {

                FileInputStream in = new FileInputStream(dirStr + i);

                byte[] buffer = new byte[1048576];
                int len;
                while ((len = in.read(buffer)) > 0)
                    out.write(buffer, 0, len);

                in.close();

                (new File(dirStr + i)).delete();
            }

            out.close();
        }

        file.File f = new file.File();
        f.setDir(dir);
        f.setName(name);
        f.setUser(userId);
        f.setSiz(Upload.getSiz(dir));
        f.setTime(formatter.format(new Date()));

        file.File.addFile(f);
        Upload.remove(dir);
        Upload.removeSiz(dir);
        response.getWriter().print("Success");
        return ;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

        return ;
    }
}


