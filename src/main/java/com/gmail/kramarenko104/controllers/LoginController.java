package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Role;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.services.CartService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/login")
@SessionAttributes(value = {"showLoginForm", "message", "user", "cart", "warning", "isAdmin"})
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    private UserService userService;
    private CartService cartService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public LoginController(UserService userService,
                           CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        modelAndView.addObject("message", null);
        modelAndView.addObject("showLoginForm", true);
        logger.debug("[eshop] LoginController.doGet....goto login.jsp");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("f_login") String login,
                                  @RequestParam("f_password") String pass,
                                  ModelAndView modelAndView) {
        String viewToGo = "login";
        boolean showLoginForm = true;
        boolean accessGranted = false;
        StringBuilder msgText = new StringBuilder();
        User currentUser = null;
        boolean isAdmin = false;

        if (em != null) {
            if ((login != null) && !("".equals(login))) {
                currentUser = userService.getUserByLogin(login);

                if (currentUser != null) {
                    String passVerif = userService.hashString(pass);
                    accessGranted = (currentUser.getPassword().equals(passVerif));
                    logger.debug("[eshop] LoginController.doPost: currentUser: " + currentUser);

                    if (accessGranted) {
                        showLoginForm = false;
                        modelAndView.addObject("user", currentUser);
                        logger.debug("[eshop] LoginController.doPost: User " + currentUser.getName() + " was registered and passed autorization");
                        logger.debug("[eshop] LoginController.doPost:currentUser.getRoles(): " +
                                currentUser.getRoles());
                        if (currentUser.getRoles().stream()
                                .map(Role::toString)
                                .collect(Collectors.joining( "," ))
                                .contains("ROLE_ADMIN")) {
                            isAdmin = true;
                        }
                        logger.debug("[eshop] LoginController.doPost: isAdmin: " +
                                isAdmin);
                        // for authorized user get the corresponding shopping Cart
                        Cart userCart = cartService.getCartByUserId(currentUser.getUserId());
                        logger.debug("[eshop] LoginController.doPost:  GOT userCart from db == " + userCart);

                        if (userCart == null) {
                            logger.debug("[eshop] LoginController.doPost: cart is empty, goto product.jsp");
                            userCart = cartService.createCart(currentUser.getUserId());
                        }
                        viewToGo = "product";
                        modelAndView.addObject("cart", userCart);
                    } else {
                        msgText.append("<b><font size=3 color='red'>Wrong password, try again!</font>");
                    }
                } else {
                    showLoginForm = false;
                    msgText.append("This user wasn't registered yet. <a href='registration'>Register</a>, please, or <a href='login'>login</a>");
                }
            }
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }

        if (!"login".equals(viewToGo)) {
            viewToGo = "redirect:/" + viewToGo;
        }

        modelAndView.addObject("showLoginForm", showLoginForm);
        modelAndView.addObject("message", msgText.toString());
        modelAndView.addObject("isAdmin", isAdmin);

        modelAndView.setViewName(viewToGo);
        logger.debug("[eshop] LoginController.doPost:   exit.......goto " + viewToGo);

        return modelAndView;
    }
}
