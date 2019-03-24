package com.gmail.kramarenko104.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public String doGet(Model model) {
        HttpSession session = session();
        session.invalidate();
        model.addAttribute("session", session);
        model.addAttribute("user", null);
        model.addAttribute("showLoginForm", true);
        model.addAttribute("message", "");
        model.addAttribute("attempt", 0);
        return "products";
    }
}
