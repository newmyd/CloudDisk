package servlet;


import user.UserMap;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;



public class Logout extends HttpServlet {

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

        UserMap.remove(userId);
        session.setAttribute("user", null);
    	
    	response.getWriter().print("Success");
        return ;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        return ;
    }
}
