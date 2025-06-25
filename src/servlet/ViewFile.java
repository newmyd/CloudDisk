package servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.io.Serial;
import java.util.ArrayList;

import javax.servlet.*;

import javax.servlet.http.*;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import file.File;


public class ViewFile extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int siz = 20;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter resp = response.getWriter();

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            return ;
        }

        int userId = (int) session.getAttribute("user");

        if (userId == 0) {
            return ;
        }

        int page = Integer.parseInt(request.getParameter("page"));
        String name = request.getParameter("name");
        String sot = request.getParameter("sort");
        name = "%" + name + "%";

        if (!sot.equals("name") && !sot.equals("name DESC") && !sot.equals("time") && !sot.equals("time DESC") && !sot.equals("siz") && !sot.equals("siz DESC"))
            return ;

        ArrayList<File> res = File.getFileByUser(userId, name, sot, page * siz - siz, siz);
        if (res == null)
            return ;

        JSONArray json = new JSONArray();
        json.add((File.getFileSize(userId, name) + siz - 1) / siz);
        for (File i : res) {
            JSONObject ob = new JSONObject();
            ob.put("id", i.getId());
            ob.put("name", i.getName());
            ob.put("time", i.getTime());
            ob.put("siz", i.getSiz());
            json.add(ob);
        }
        resp.print(json);
        return ;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

        return ;
    }
}
