package servlet;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;

import user.User;



public class CheckUser extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        Integer userId = (Integer) session.getAttribute("user");
        
        if (userId == null || userId == 0) {
	        response.getWriter().print("");
	        return ;
        }

        response.getWriter().print(User.getUserById(userId).getUserName());
    	return ;
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        doPost(request, response);
        
        return ;
    }
}
