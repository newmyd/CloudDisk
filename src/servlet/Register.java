package servlet;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;

import user.User;



public class Register extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");

        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        if (userName == null || userName.equals("")) {
            response.getWriter().print("Username");
            return ;
        }

        if (passWord == null || passWord.equals("")) {
            response.getWriter().print("Password");
            return ;
        }

        try {

            if (User.getUserByName(userName) != null) {
                response.getWriter().print("Username");
                return ;
            }

            User.registerUser(userName, passWord);

            response.getWriter().print("Success");

            return ;

        } catch(Exception e) {

            response.getWriter().print("Database");

            return ;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

        return ;
    }
}
