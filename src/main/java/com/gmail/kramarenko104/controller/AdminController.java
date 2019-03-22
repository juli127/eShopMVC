package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DaoFactory daoFactory;
    private static Logger logger = Logger.getLogger(AdminController.class);

    public AdminController() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    // REST mapping:    //admin/users/3
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    protected ModelAndView getAllUsers()  {
        HttpSession session = session();
        ModelAndView model = new ModelAndView("admin");
        if (session != null) {
            daoFactory.openConnection();
            UserDao userDao = daoFactory.getUserDao();
            List<User> usersList = userDao.getAllUsers();
            model.addObject("usersList", usersList);
            daoFactory.deleteUserDao(userDao);
            daoFactory.closeConnection();
        }
        return model;
    }
}
