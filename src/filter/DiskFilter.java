package filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DiskFilter implements Filter  {
    public void  init(FilterConfig config) throws ServletException {
    }
    public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletResponse tmp = (HttpServletResponse)response;
        tmp.sendRedirect("/index.html");
//        chain.doFilter(request,response);
    }
    public void destroy() {
    }
}