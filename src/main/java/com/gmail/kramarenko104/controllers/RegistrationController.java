package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/registration")
@SessionAttributes(value = {"cart", "user", "login", "name", "address", "comment", "errorsMsg", "warning"})
public class RegistrationController {

    private static Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private static final String DB_WARNING = "Check your connection to DB!";
    private UserService userService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet() {
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("login") String login,
                            @RequestParam("password") String pass,
                            @RequestParam("repassword") String repass,
                            @RequestParam("name") String name,
                            @RequestParam("address") String address,
                            @RequestParam("comment") String comment,
                            @RequestParam("role") String role) {

        String viewToGo = "registration";
        ModelAndView modelAndView = new ModelAndView();

        if (em != null) {
            boolean needRegistration = true;
            boolean userExist = false;
            StringBuilder message = new StringBuilder();
            Map<String, String> errors = new HashMap<>();
            StringBuilder errorsMsg = new StringBuilder();

            if (!"".equals(login)) {
                // check if user with this login/password is already registered
                userExist = (userService.getUserByLogin(login) != null);

                // user with this login/password wasn't registered yet
                if (!userExist) {
                    logger.debug("[eshop] RegisrtCntrl: user with entered login wasn't registered yet");
                    Map<String, String> regData = new HashMap<>();
                    regData.put("login", login);
                    regData.put("pass", pass);
                    regData.put("repass", repass);
                    regData.put("name", name);
                    regData.put("address", address);
                    regData.put("role", role);

                    regData.entrySet()
                            .parallelStream()
                            .filter(e -> e.getValue() == null || e.getValue().length() < 1)
                            .forEach(e -> errors.put(e.getKey(), "cannot be empty!"));

                    if (repass.length() > 0 && !pass.equals(repass)) {
                        errors.put("", "Password and retyped one don't match!");
                    }

                    if (pass.length() < 4) {
                        errors.put("", "Password should have minimum 4 symbols!");
                    }

                    String patternString = "([0-9a-zA-Z._-]+@[0-9a-zA-Z_-]+[.]{1}[a-z]+)";
                    Pattern pattern = Pattern.compile(patternString);
                    Matcher matcher = pattern.matcher(login);
                    if (!matcher.matches()) {
                        errors.put("", "e-mail should have correct format");
                    }

                    if (errors.size() == 0) {
                        // all fields on registration form are filled correctly
                        User newUser = new User();
                        newUser.setLogin(login);
                        newUser.setName(name);
                        newUser.setPassword(pass);
                        newUser.setAddress(address);
                        newUser.setComment(comment);
                        newUser.setRole(role);
                        newUser = userService.createUser(newUser);

                        if (newUser != null) {
                            logger.debug("[eshop] RegisrtServlet: new user was created: " + newUser);
                            message.append("<br><font color='green'><center>Hi, " + name + "! <br>You have been registered. You can shopping now!</font>");
                            modelAndView.addObject("user", newUser);
                            newUser.setRole("ROLE_USER");
                            Cart newCart = new Cart();
                            newCart.setUser(newUser);
                            modelAndView.addObject("cart", newCart);
                        } else {
                            logger.debug("[eshop] RegisrtServlet: new user was NOT created " );
                            modelAndView.addObject("user", null);
                            modelAndView.addObject("cart", null);
                        }
                        needRegistration = false;
                    }
                    // some fields on registration form are filled in wrong way
                    else {
                        // prepare errorsMsg to show on registration.jsp
                        errors.entrySet()
                                .parallelStream()
                                .forEach(e -> errorsMsg.append(e.getKey()).append(" ").append(e.getValue()).append("<br>"));
                    }
                }
                // user with this login/password was registered already
                else {
                    needRegistration = false;
                }
            }
            modelAndView.addObject("RegMessage", message.toString());

            if (needRegistration) {
                // save some entered fields not to force user enter them again
                modelAndView.addObject("login", login);
                modelAndView.addObject("name", name);
                modelAndView.addObject("address", address);
                modelAndView.addObject("comment", comment);
                modelAndView.addObject("errorsMsg", errorsMsg);
            } else {
                // user with this login/password is already registered, send user to /login
                if (userExist) {
                    logger.debug("[eshop] RegisrtServlet: user was already registered before, send user to login page");
                    viewToGo = "redirect:/login";
                } else {
                    // it's the new fresh-registered user, send user to /products
                    viewToGo = "redirect:/products";
                }
                modelAndView.addObject("name", null);
                modelAndView.addObject("address", null);
                modelAndView.addObject("comment", null);
                modelAndView.addObject("errorsMsg", null);
            }
        } else {
            modelAndView.addObject("warning", DB_WARNING);
        }
        modelAndView.setViewName(viewToGo);
        return modelAndView;
    }
}
