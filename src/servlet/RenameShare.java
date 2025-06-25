package servlet;


import com.alibaba.fastjson2.JSONArray;
import share.Share;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;


public class RenameShare extends HttpServlet {
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
        String name = request.getParameter("name");

        JSONArray json = JSONArray.parseArray(request.getParameter("json"));
        for (Object i : json) {
            int id = (Integer) i;
            file = Share.getFileById(id);
            if (file == null) {
                continue;
            }
            if (userId == file.getUser() || userId == file.getSrc())
                Share.setFile(id, name);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
