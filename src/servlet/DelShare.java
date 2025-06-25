package servlet;



import com.alibaba.fastjson2.JSONArray;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;

import file.File;
import share.Share;

import disk.Disk;



public class DelShare extends HttpServlet {
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

        Share file;

        JSONArray json = JSONArray.parseArray(request.getParameter("json"));
        for (Object i : json) {
            int id = (Integer) i;
            file = Share.getFileById(id);
            if (file == null)
                continue;
            if (userId == file.getUser() || userId == file.getSrc()) {
                Share.delFile(id);
                if (!File.getFileByDir(file.getDir()) && !Share.getFileByDir(file.getDir()))
                    Disk.delete(request.getServletContext().getRealPath("/disk/" + file.getDir() + "/"));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
