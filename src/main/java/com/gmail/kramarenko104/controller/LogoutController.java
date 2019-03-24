package com.gmail.kramarenko104.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    public LogoutController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(Model model) {
        model.addAttribute("user", null);
        model.addAttribute("showLoginForm", true);
        model.addAttribute("message", "");
        model.addAttribute("attempt", 0);
        return "products";
    }
}
