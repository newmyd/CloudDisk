package servlet;



import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;

import user.User;



public class SetUser extends HttpServlet {

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
        
        User user;
		user = User.getUserById(userId);
		if (user == null) {
			response.getWriter().print("Database");
			return ;
		}
        
        String passWord = request.getParameter("passWord");
        String newUserName = request.getParameter("newUserName");
        String newPassWord = request.getParameter("newPassWord");
		
        if (passWord == null || !passWord.equals(user.getPassWord())) {
        	
    		response.getWriter().print("Password");
    		
        	return ;
        }

        
    	if (newPassWord != null && !newPassWord.equals("")) {
			user.modifyPassWord(newPassWord);
    	}
    	
    	if (newUserName != null && !newUserName.equals("")) {
			if (user.modifyUserName(newUserName) == null) {
				response.getWriter().print("NewUsername");
				return ;
			}
    	}

		response.getWriter().print("Success");
        return ;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        doPost(request, response);
        
        return ;
    }
}
