package servlet;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.*;

import javax.servlet.http.*;

import user.User;
import user.UserMap;


public class Login extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        if (session.getAttribute("user") != null) {
            Integer tmp = (Integer) session.getAttribute("user");
            if (tmp != null && tmp != 0) {
	    		response.getWriter().print("Logined");
	        	return ;
        	}
        }
        
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        User user;

        try {
        	user = User.getUserByName(userName);
        } catch(Exception e) {
            response.getWriter().print("Database");
            return ;
        }


        if (user == null || !userName.equals(user.getUserName())) {
            response.getWriter().print("Username");
            return ;
        }

        if (passWord == null || !passWord.equals(user.getPassWord())) {
            response.getWriter().print("Password");
            return ;
        }

        HttpSession ses = UserMap.get(user.getUserId());


        try {
            if (ses != null)
                ses.setAttribute("user", null);
        } catch (Exception e) {
            
        }



        session.setAttribute("user", user.getUserId());

        UserMap.put(user.getUserId(), session);

        response.getWriter().print("Success");

        return ;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        doPost(request, response);
        
        return ;
    }
}
