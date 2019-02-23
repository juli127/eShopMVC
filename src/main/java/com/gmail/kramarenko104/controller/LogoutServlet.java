package com.gmail.kramarenko104.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet (name = "WebServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    public LogoutServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
//        session.setAttribute("logout", "logged out");
        session.invalidate();
        session = request.getSession();
        session.setAttribute("user", null);
        session.setAttribute("showLoginForm", true);
        session.setAttribute("message", "");
        session.setAttribute("attempt", 0);

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/products.jsp");
        rd.forward(request, response);
    }
}
