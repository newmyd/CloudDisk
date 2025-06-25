package servlet;



import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.*;

import javax.servlet.http.*;

import file.File;



public class Download extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String fileName = "0";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/octet-stream;charset=UTF-8");

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            return ;
        }

        int userId = (int) session.getAttribute("user");

        if (userId == 0) {
            return ;
        }

        File fil;
        int id = Integer.parseInt(request.getParameter("id"));

        fil = File.getFileById(id);
        if (fil == null) {
            return ;
        }

        if (userId != fil.getUser()) {
            return;
        }


        String dataDirectory = request.getServletContext().getRealPath("/disk/" + fil.getDir() + "/");
        java.io.File file = new java.io.File(dataDirectory, fileName);
        if (file.exists()) {
            response.setHeader("Content-Length", Long.toString(file.length()));
            response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fil.getName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20") + "\"");
            byte[] buffer = new byte[1048576];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            bis.close();
            fis.close();
        }
        return ;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

        return ;
    }
}
