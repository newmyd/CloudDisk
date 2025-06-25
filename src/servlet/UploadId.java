package servlet;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;

import upload.Upload;
import user.User;


public class UploadId extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

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

        int id = Upload.getId();

        if (id == -1) {
            response.getWriter().print("Database");
            return ;
        }

        Upload.put(id, userId);

        response.getWriter().print(id);

        return ;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

        return ;
    }
}



/*

                        int fileId = 0;
                        try {
                            fileId = FileDao.getSize() + 1;
                        } catch (Exception e) {
                            response.getWriter().print("Database");
                            return ;
                        }

                        String filePath = request.getServletContext().getRealPath("./disk") + java.io.File.separator + fileId;





                        InputStream ins = item.getInputStream();
                        FileOutputStream fouts = new FileOutputStream(filePath);

                        byte buffer[] = new byte[1048576];
                        int len;
                        while ((len = ins.read(buffer)) > 0)
                            fouts.write(buffer, 0, len);
                        ins.close();
                        fouts.close();


//                        item.write(new java.io.File(filePath));


                        file.File f = new file.File();
                        f.setDir(fileId);
                        f.setName(item.getName());
                        f.setUser(user.getUserName());
                        f.setSiz(item.getSize());
                        f.setTime(formatter.format(new Date()));
                        item.delete();
                        FileDao.addFile(f);
 */