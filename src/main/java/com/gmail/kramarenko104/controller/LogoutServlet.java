package com.gmail.kramarenko104.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/logout")
public class LogoutServlet extends HttpServlet {

    public LogoutServlet() {
    }

    private static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {
        HttpSession session = getSession();
        session.invalidate();
        session = getSession();
        model.put("user", null);
        model.put("showLoginForm", true);
        model.put("message", "");
        model.put("attempt", 0);
        return "products";
    }
}
