package com.gmail.kramarenko104.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/forbidden")
public class ForbiddenController {

    public ForbiddenController() {
    }

    protected ModelAndView doGet() {
        ModelAndView model = new ModelAndView("WEB-INF/view/forbidden.jsp");
        return model;
    }
}
