package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static Logger logger = Logger.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    public RegistrationController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet() {
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(@RequestParam("login") String login,
                            @RequestParam("password") String pass,
                            @RequestParam("repassword") String repass,
                            @RequestParam("name") String name,
                            @RequestParam("address") String address,
                            @RequestParam("comment") String comment,
                            Model model) {

        StringBuilder message = new StringBuilder();
        Map<String, String> errors = new HashMap<>();
        StringBuilder errorsMsg = new StringBuilder();
        boolean needRegistration = true;
        boolean userExist = false;
        String viewToGo = "registration";

        if (!"".equals(login)) {
            // check if user with this login/password is already registered
            userExist = (userService.getUserByLogin(login) != null);

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
                    if (userService.createUser(newUser)) {
                        newUser = userService.getUserByLogin(login);
                        logger.debug("RegisrtServlet: new user was created: " + newUser);
                        message.append("<br><font color='green'><center>Hi, " + name + "! <br>You have been registered. You can shopping now!</font>");
                        model.addAttribute("user", newUser);
                        model.addAttribute("userCart", new Cart(newUser.getId()));
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
        }

        model.addAttribute("RegMessage", message.toString());

        if (needRegistration) {
            // save some entered fields not to force user enter them again
            model.addAttribute("login", login);
            model.addAttribute("name", name);
            model.addAttribute("address", address);
            model.addAttribute("comment", comment);
            model.addAttribute("errorsMsg", errorsMsg);
            //req.getRequestDispatcher("WEB-INF/view/registration.jsp").forward(req, resp);
        } else {
            // user with this login/password is already registered, send user to /login
            if (userExist) {
                logger.debug("RegisrtServlet: user was already registered before, send to login page");
                //resp.sendRedirect("./login");
                viewToGo = "redirect:/login";
            }
            model.addAttribute("name", null);
            model.addAttribute("address", null);
            model.addAttribute("comment", null);
            model.addAttribute("errorsMsg", null);
        }

        return viewToGo;
    }
}
