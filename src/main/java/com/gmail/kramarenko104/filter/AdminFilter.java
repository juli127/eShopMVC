package com.gmail.kramarenko104.filter;

import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class AdminFilter implements Filter {

    private static Logger logger = Logger.getLogger(AdminFilter.class);

    public AdminFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        HttpSession session = hreq.getSession();
        if(session.getAttribute("user") == null || !(boolean)session.getAttribute("isAdmin")) {
            req.getRequestDispatcher("/forbidden").forward(req, resp);
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
