package com.gmail.kramarenko104.filter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


// действует на все сервлеты
public class MyFilter implements Filter {

    private String param1;

    public MyFilter() {
    }


    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter out = new PrintWriter(new File("myLog.txt"));
        out.write("param1 = " + param1 + "\nIP:" + request.getRemoteAddr() + "\nTime:" + new Date().toString());
        System.out.println("param1 = " + param1 + "\nIP:" + request.getRemoteAddr() + "\nTime:" + new Date().toString());
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpSession session = hreq.getSession();
        if(session.getAttribute("user") == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/NotFound");
            rd.forward(request, response);
        }
        out.close();
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        param1 = fConfig.getInitParameter("param1");
    }

}
