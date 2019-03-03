package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/registration")
public class RegistrationServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistrationServlet.class);
    private DaoFactory daoFactory;

    public RegistrationServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    private static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(ModelMap model) {
        HttpSession session = getSession();
        StringBuilder message = new StringBuilder();
        boolean needRegistration = false;

        String login = (String) model.get("login");
        String pass = (String) model.get("password");
        String repass = (String) model.get("repassword");
        String name = (String) model.get("name");
        String address = (String) model.get("address");
        String comment = (String) model.get("comment");

        Map<String, String> regData = new HashMap<>();
        regData.put("login", login);
        regData.put("pass", pass);
        regData.put("repass", repass);
        regData.put("name", name);
        regData.put("address", address);

        Map<String, String> errors = new HashMap<>();

        // already logged in
        if (session.getAttribute("user") != null) {
            logger.debug("RegistrServlet: some user is already logged in");
            User currentUser = (User) session.getAttribute("user");
            String currentLogin = (currentUser).getLogin();
            if (currentLogin.equals(login)) {
                logger.debug("RegistrServlet: logged in user tries to registere. Just forward him to page with products");
                message.append("<br><b><font color='green'><center>Hi, " + currentUser.getName() + ". <br>You are registered already.</font><b>");
            } else { // we should logout and login as the new user
                logger.debug("RegistrServlet: try to register when user is logged in. Then logout and forward to registartion.jsp");
                session.invalidate();
                session = getSession();
                needRegistration = true;
            }
        } else {// not logged in yet
            logger.debug("RegistrServlet: no one user is logged in. Just check entered fields from registration form");

            for (Map.Entry<String, String> entry : regData.entrySet()) {
                if (entry.getValue().length() < 1) {
                    errors.put(entry.getKey(), "Cannot be empty!");
                }
            }
            regData = null;

            if (repass.length() > 0 && !pass.equals(repass)) {
                errors.put("regRepassword", "Password and retyped one don't match!");
            }

            String patternString = "([0-9a-zA-Z]+){4,}";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(pass);
            if (pass.length() > 0 && !matcher.matches()) {
                errors.put("regPassword", "Password should has minimum 4 symbols with at least one upper case letter and 1 digit!");
            }

            if (errors.size() == 0) {
                UserDao userDao = daoFactory.getUserDao();
                User newUser = new User();
                newUser.setLogin(login);
                newUser.setPassword(pass);
                newUser.setAddress(address);
                newUser.setComment(comment);
                if (userDao.createUser(newUser)) {
                    message.append("<br><font color='green'><center>Hi, " + name + "! <br>You are registered now.</font>");
                    model.put("user", newUser);
                    model.put("userName", newUser.getName());
                } else {
                    needRegistration = true;
                    message.append("<br><font color='red'><center>User wan't registered because of DB problems</font>");
                }
                daoFactory.deleteUserDao(userDao);
            } else {
                needRegistration = true;
                model.put("regErrors", errors);
            }
        }
        model.put("message", message.toString());

        if (needRegistration) {
            return "registration";
        } else {
            return "products";
        }
    }

    // where to close connection???
    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }

}
