package com.gmail.kramarenko104.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    public LogoutController() {
    }


    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet() {
        HttpSession session = session();
        session.invalidate();
        session.setAttribute("user", null);

        ModelAndView model = new ModelAndView("products");
        model.addObject("showLoginForm", true);
        model.addObject("message", "");
        model.addObject("attempt", 0);
        return model;
    }
}
