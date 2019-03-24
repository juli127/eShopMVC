package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartService;
import com.gmail.kramarenko104.service.UserService;
import com.gmail.kramarenko104.service.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int WAIT_SECONDS_BEFORE_LOGIN_FORM_RELOAD = 15;
    private static final String adminLog = "admin";

    @Autowired
    private DaoFactory daoFactory;
    private int attempt;

    public LoginController() {
        //daoFactory = DaoFactory.getSpecificDao();
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet(ModelMap model) {
        HttpSession session = session();
        model.addAttribute("showLoginForm", true);
        model.addAttribute("message", null);
        model.addAttribute("attempt", null);
        return new ModelAndView("forward:/WEB-INF/view/login.jsp");
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest req, ModelMap model)  {
        HttpSession session = session();
        ModelAndView modelAndView = new ModelAndView("WEB-INF/view/login.jsp");
        daoFactory.openConnection();
        boolean showLoginForm = true;
        boolean accessGranted = false;
        StringBuilder msgText = new StringBuilder();
        boolean isAdmin = false;
        User currentUser = null;

        String viewToGo = "WEB-INF/view/login.jsp";
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (session != null) {
            attempt = (session.getAttribute("attempt") == null) ? 0 : (int) session.getAttribute("attempt");

            // already logged in
            if (session.getAttribute("user") != null) {
                currentUser = (User) session.getAttribute("user");
                accessGranted = true;
            } // not logged in yet
            else {
                long waitTime = 0;

                if ((login != null) && !("".equals(login))) {
                    session.setAttribute("login", login);
                    UserService userService = daoFactory.getUserService();
                    currentUser = userService.getUserByLogin(login);
                    boolean exist = (currentUser != null);

                    if (exist) {
                        String passVerif = UserServiceImpl.hashString(pass);
                        accessGranted = (currentUser.getPassword().equals(passVerif));
                        showLoginForm = !accessGranted && attempt < MAX_LOGIN_ATTEMPTS;

                        if (accessGranted) {
                            attempt = 0;
                            showLoginForm = false;
                            session.setAttribute("user", currentUser);
                            session.setAttribute("login", null);
                            logger.debug("LoginServlet: User " + currentUser.getName() + " was registered and passed autorization");
                            if (adminLog.equals(login) && userService.getUserByLogin(adminLog).getPassword().equals(passVerif)){
                                isAdmin = true;
                            }
                        } else {
                            attempt++;
                            if (attempt >= MAX_LOGIN_ATTEMPTS) {
                                if (attempt == MAX_LOGIN_ATTEMPTS) {
                                    session.setAttribute("startTime", System.currentTimeMillis());
                                }
                                waitTime = WAIT_SECONDS_BEFORE_LOGIN_FORM_RELOAD - (System.currentTimeMillis() - (Long)session.getAttribute("startTime" ))/ 1000;
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
                        msgText.append("<br>This user wasn't registered yet. <a href='registration'>Register, please,</a> or <a href='login'>login</a>");
                    }
                    daoFactory.deleteUserService(userService);
                } else {
                    attempt = 0;
                }
            }
        }
        // for authorized user get the corresponding shopping Cart
        if (accessGranted) {
            CartService cartService = daoFactory.getCartService();
            showLoginForm = false;
            Cart userCart = (Cart) session.getAttribute("userCart");
            if (userCart == null) {
                userCart = cartService.getCart(currentUser.getId());
                if (userCart == null) {
                    userCart = new Cart(currentUser.getId());
                }
                session.setAttribute("userCart", userCart);
            }
            if (userCart.getItemsCount() > 0) {
                viewToGo = "./cart";
            } else {
                viewToGo = "./product";
            }
            daoFactory.deleteCartService(cartService);
        }

        daoFactory.closeConnection();

        session.setAttribute("showLoginForm", showLoginForm);
        session.setAttribute("message", msgText.toString());
        session.setAttribute("attempt", attempt);
        session.setAttribute("isAdmin", isAdmin);

        if ("WEB-INF/view/login.jsp".equals(viewToGo)){
//            req.getRequestDispatcher(viewToGo).forward(req, resp);
//            model = new ModelAndView("forward:login");
            modelAndView = new ModelAndView("forward:login" + viewToGo, model);
        } else {
//            resp.sendRedirect(viewToGo);
            modelAndView = new ModelAndView("redirect:" + viewToGo, model);
        }
        return modelAndView;
    }
}
