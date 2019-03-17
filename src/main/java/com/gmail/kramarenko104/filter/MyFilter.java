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


public class MyFilter implements Filter {

    private FilterConfig config = null;
    private static Logger logger = Logger.getLogger(MyFilter.class);

    public MyFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
        this.config = config;
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
