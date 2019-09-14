package com.gmail.kramarenko104.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/forbidden")
public class ForbiddenController {

    public ForbiddenController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet() {
        return new ModelAndView("forbidden");
    }
}
