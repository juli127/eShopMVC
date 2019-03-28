package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static Logger logger = Logger.getLogger(AdminController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    @Autowired
    private UserService userService;

    public AdminController() {
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        if (userService.sessionIsOpen()) {
            List<User> usersList = userService.getAllUsers();
            model.addAttribute("usersList", usersList);
        } else { // connection to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return "admin";
    }
}
