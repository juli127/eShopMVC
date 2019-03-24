package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.service.UserDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static Logger logger = Logger.getLogger(RegistrationController.class);

    @Autowired
    private DaoFactory daoFactory;

    public RegistrationController() {

        //daoFactory = DaoFactory.getSpecificDao();
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet() {
        return new ModelAndView("WEB-INF/view/registration.jsp");
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = session();
        ModelAndView model = null;
        daoFactory.openConnection();

        StringBuilder message = new StringBuilder();
        Map<String, String> errors = new HashMap<>();
        StringBuilder errorsMsg = new StringBuilder();
        boolean needRegistration = true;
        boolean userExist = false;

        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        String repass = req.getParameter("repassword");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String comment = req.getParameter("comment");

        if (session != null) {
            if (!"".equals(login)) {
                // check if user with this login/password is already registered
                UserDao userDao = daoFactory.getUserService();
                userExist = (userDao.getUserByLogin(login) != null);

                // user with this login/password wasn't registered yet
                if (!userExist) {
                    logger.debug("RegisrtServlet: user with entered login wasn't registered yet");
                    Map<String, String> regData = new HashMap<>();
                    regData.put("login", login);
                    regData.put("pass", pass);
                    regData.put("repass", repass);
                    regData.put("name", name);
                    regData.put("address", address);

                    for (Map.Entry<String, String> entry : regData.entrySet()) {
                        logger.debug("RegisrtServlet: user entered: " + entry.getKey() + ": " + entry.getValue());
                        if (entry.getValue() == null || entry.getValue().length() < 1) {
                            errors.put(entry.getKey(), "Cannot be empty!");
                        }
                    }
                    if (repass.length() > 0 && !pass.equals(repass)) {
                        errors.put("", "Password and retyped one don't match!");
                    }

                    String patternString = "([0-9a-zA-Z]+@[0-9a-zA-Z.]){4,}";
                    Pattern pattern = Pattern.compile(patternString);
                    Matcher matcher = pattern.matcher(pass);
                    if (pass.length() > 0 && !matcher.matches()) {
                        errors.put("", "Password should have minimum 4 symbols!");
                    }

                    if (errors.size() == 0) {
                        // all fields on registration form are filled correctly
                        User newUser = new User();
                        newUser.setLogin(login);
                        newUser.setName(name);
                        newUser.setPassword(pass);
                        newUser.setAddress(address);
                        newUser.setComment(comment);
                        if (userDao.createUser(newUser)) {
                            newUser = userDao.getUserByLogin(login);
                            logger.debug("RegisrtServlet: new user was created: " + newUser);
                            message.append("<br><font color='green'><center>Hi, " + name + "! <br>You have been registered. You can shopping now!</font>");
                            session.setAttribute("user", newUser);
                            session.setAttribute("userCart", new Cart(newUser.getId()));
                            needRegistration = false;
                        } else {
                            message.append("<br><font color='red'><center>User wan't registered because of DB problems! Try a bit later.</font>");
                        }
                    }
                    // some fields on registration form are filled in wrong way
                    else {
                        // prepare errorsMsg to show on registration.jsp
                        errorsMsg.append("<ul>");
                        for (Map.Entry<String, String> entry : errors.entrySet()) {
                            errorsMsg.append("<li>").append(entry.getKey()).append(" ").append(entry.getValue()).append("</li>");
                        }
                        errorsMsg.append("</ul>");
                    }
                }
                // user with this login/password was registered already
                else {
                    needRegistration = false;
                }
                daoFactory.deleteUserDao(userDao);
            }

            session.setAttribute("RegMessage", message.toString());

            model = new ModelAndView("WEB-INF/view/registration.jsp");
            if (needRegistration) {
                // save some entered fields not to force user enter them again
                model.addObject("login", login);
                model.addObject("name", name);
                model.addObject("address", address);
                model.addObject("comment", comment);
                model.addObject("errorsMsg", errorsMsg);
//                req.getRequestDispatcher("WEB-INF/view/registration.jsp").forward(req, resp);

            } else {
                // user with this login/password is already registered, send user to /login
                if (userExist) {
                    logger.debug("RegisrtServlet: user was already registered before, send to login page");
//                    resp.sendRedirect("./login");
                    model = new ModelAndView("./login");
                }
                model.addObject("name", null);
                model.addObject("address", null);
                model.addObject("comment", null);
                model.addObject("errorsMsg", null);
            }
        }
        daoFactory.closeConnection();
        return model;
    }
}
