package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.model.Cart;
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

@Controller
@RequestMapping("/login")
@SessionAttributes(value = {"showLoginForm", "message", "attempt", "user", "login", "startTime", "cart", "isAdmin", "warning"})
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int WAIT_SECONDS_BEFORE_LOGIN_FORM_RELOAD = 15;
    private static final String DB_WARNING = "Check your connection to DB!";
    private static final String adminLog = "admin";
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
    protected ModelAndView doGet() {
        logger.debug("[eshop] LoginController.doGet:   enter ....goto login.jsp......");
        return new ModelAndView("login");
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("login") String login,
                                  @RequestParam("password") String pass) {
        logger.debug("[eshop] LoginController.doPost:   enter .........." );
        String viewToGo = "login";
        ModelAndView modelAndView = new ModelAndView();
        boolean showLoginForm = true;
        boolean accessGranted = false;
        StringBuilder msgText = new StringBuilder();
        boolean isAdmin = false;
        User currentUser = null;
        int attempt;

        if (em != null) {
            Object attemptObj = modelAndView.getModel().get("attempt");
            attempt = (attemptObj == null) ? 0 : (int) attemptObj;
            currentUser = (User) modelAndView.getModel().get("user");
            logger.debug("[eshop] LoginController.doPost:   currentUser from session = " + currentUser);

            // already logged in
            if (currentUser != null && currentUser.getLogin() != null) {
                logger.debug("[eshop] LoginController.doPost:   already logged in.... " );
                accessGranted = true;
            } // not logged in yet
            else {
                logger.debug("[eshop] LoginController.doPost:   not logged in yet.... " );
                long waitTime = 0;
                if ((login != null) && !("".equals(login))) {
                    modelAndView.addObject("login", login);
                    currentUser = userService.getUserByLogin(login);
                    boolean exist = (currentUser != null);

                    if (exist) {
                        String passVerif = userService.hashString(pass);
                        accessGranted = (currentUser.getPassword().equals(passVerif));
                        showLoginForm = !accessGranted && attempt < MAX_LOGIN_ATTEMPTS;
                        logger.debug("[eshop] LoginController.doPost: currentUser: " + currentUser);

                        if (accessGranted) {
                            attempt = 0;
                            showLoginForm = false;
                            modelAndView.addObject("user", currentUser);
                            modelAndView.addObject("login", null);
                            logger.debug("[eshop] LoginController.doPost: User " + currentUser.getName() + " was registered and passed autorization");
                            if (adminLog.equals(login) && userService.getUserByLogin(adminLog).getPassword().equals(passVerif)) {
                                isAdmin = true;
                            }
                        } else {
                            attempt++;
                            if (attempt >= MAX_LOGIN_ATTEMPTS) {
                                if (attempt == MAX_LOGIN_ATTEMPTS) {
                                    modelAndView.addObject("startTime", System.currentTimeMillis());
                                }
                                waitTime = WAIT_SECONDS_BEFORE_LOGIN_FORM_RELOAD - (System.currentTimeMillis() - (Long) modelAndView.getModel().get("startTime")) / 1000;
                                if (waitTime > 0) {
                                    msgText.append("<br><font size=3 color='red'><b> Attempts' limit is exceeded. Login form will be available in " + waitTime + " seconds</b></font>");
                                    showLoginForm = false;
                                } else {
                                    attempt = 0;
                                    showLoginForm = true;
                                }
                            } else if (attempt >= 0) {
                                msgText.append("<b><font size=3 color='red'>Wrong password, try again! You have 3 attempts. (attempt #" + attempt + ")</font>");
                            }
                        }
                    } else {
                        attempt = 0;
                        showLoginForm = false;
                        msgText.append("<br>This user wasn't registered yet. <a href='registration'>Register</a>, please, or <a href='login'>login</a>");
                    }
                } else {
                    attempt = 0;
                }
            }

            // for authorized user get the corresponding shopping Cart
            if (accessGranted) {
                showLoginForm = false;
                Cart userCart = cartService.getCartByUserId(currentUser.getUserId());
                logger.debug("[eshop] LoginController.doPost:  GOT userCart from db == " + userCart);

                if (userCart == null) {
                    logger.debug("[eshop] LoginController.doPost: cart is empty, goto product.jsp");
                    userCart = cartService.createCart(currentUser.getUserId());
                }
                viewToGo = "product";
                modelAndView.addObject("cart", userCart);
            }
            modelAndView.addObject("showLoginForm", showLoginForm);
            modelAndView.addObject("message", msgText.toString());
            modelAndView.addObject("attempt", attempt);
            modelAndView.addObject("isAdmin", isAdmin);
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }

        if (!"login".equals(viewToGo)) {
            viewToGo = "redirect:" + viewToGo;
        }
        modelAndView.setViewName(viewToGo);
        logger.debug("[eshop] LoginController.doPost:   exit.......goto " + viewToGo);
        return modelAndView;
    }
}