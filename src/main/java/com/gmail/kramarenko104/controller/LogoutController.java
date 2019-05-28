package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logout")
@SessionAttributes(value = {"user", "showLoginForm", "message", "attempt", "cart", "order", "isAdmin"})
public class LogoutController {

    public LogoutController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet() {
        ModelAndView modelAndView = new ModelAndView("products");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("showLoginForm", true);
        modelAndView.addObject("message", "");
        modelAndView.addObject("attempt", 0);
        modelAndView.addObject("cart", new Cart());
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("isAdmin", false);
        return modelAndView;
    }
}
