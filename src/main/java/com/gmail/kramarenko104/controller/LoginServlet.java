package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.dao.UserDaoMySqlImpl;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(LoginServlet.class);
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int WAIT_SECONDS_BEFORE_LOGIN_FORM_RELOAD = 15;
    private static final String adminLog = "admin";
    private DaoFactory daoFactory;
    private int attempt;

    public LoginServlet() throws ServletException, IOException {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("showLoginForm", true);
        session.setAttribute("message", null);
        session.setAttribute("attempt", null);
        req.getRequestDispatcher("WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
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
                    UserDao userDao = daoFactory.getUserDao();
                    currentUser = userDao.getUserByLogin(login);
                    boolean exist = (currentUser != null);

                    if (exist) {
                        String passVerif = UserDaoMySqlImpl.hashString(pass);
                        accessGranted = (currentUser.getPassword().equals(passVerif));
                        showLoginForm = !accessGranted && attempt < MAX_LOGIN_ATTEMPTS;

                        if (accessGranted) {
                            attempt = 0;
                            showLoginForm = false;
                            session.setAttribute("user", currentUser);
                            session.setAttribute("login", null);
                            logger.debug("LoginServlet: User " + currentUser.getName() + " was registered and passed autorization");
                            if (adminLog.equals(login) && userDao.getUserByLogin(adminLog).getPassword().equals(passVerif)){
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
                    daoFactory.deleteUserDao(userDao);
                } else {
                    attempt = 0;
                }
            }
        }
        // for authorized user get the corresponding shopping Cart
        if (accessGranted) {
            CartDao cartDao = daoFactory.getCartDao();
            showLoginForm = false;
            Cart userCart = (Cart) session.getAttribute("userCart");
            if (userCart == null) {
                userCart = cartDao.getCart(currentUser.getId());
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
            daoFactory.deleteCartDao(cartDao);
        }

        daoFactory.closeConnection();

        session.setAttribute("showLoginForm", showLoginForm);
        session.setAttribute("message", msgText.toString());
        session.setAttribute("attempt", attempt);
        session.setAttribute("isAdmin", isAdmin);

        if ("WEB-INF/view/login.jsp".equals(viewToGo)){
            req.getRequestDispatcher(viewToGo).forward(req, resp);
        } else {
            resp.sendRedirect(viewToGo);
        }
    }
}
