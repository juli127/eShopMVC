package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.factoryDao.DaoFactory;
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

    @Autowired
    private DaoFactory daoFactory;
    private static Logger logger = Logger.getLogger(AdminController.class);

    public AdminController() {
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    protected String getAllUsers(Model model)  {
        if (model.asMap().get("session") != null) {
            daoFactory.openConnection();
            UserService userService = daoFactory.getUserService();
            List<User> usersList = userService.getAllUsers();
            model.addAttribute("usersList", usersList);
            daoFactory.deleteUserService(userService);
            daoFactory.closeConnection();
        }
        return "admin";
    }
}
