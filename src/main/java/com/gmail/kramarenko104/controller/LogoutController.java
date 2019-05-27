package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logout")
@SessionAttributes(value = {"user", "showLoginForm", "message", "attempt", "cart", "order"})
public class LogoutController {

    public LogoutController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(ModelAndView modelAndView,
                              @ModelAttribute(name = "user") User currentUser) {
        modelAndView.setViewName("products");
        System.out.println("LogoutController.GET:   enter.. currentUser: " + currentUser);
        System.out.println("LogoutController.GET:   enter.. modelAndView.getModel().containsKey(user): " + modelAndView.getModel().containsKey("user"));
//        if (!modelAndView.getModel().containsKey("user")) {
//            modelAndView.addObject("user", null);
//            modelAndView.addObject("cart", null);
//        }
        modelAndView.addObject("user", new User());
        modelAndView.addObject("showLoginForm", true);
        modelAndView.addObject("message", "");
        modelAndView.addObject("attempt", 0);
        modelAndView.addObject("cart", new Cart());
        modelAndView.addObject("order", new Order());
        System.out.println("LogoutController.GET:   exit.. user: " + modelAndView.getModel().get("user"));
        return modelAndView;
    }
}
