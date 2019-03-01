package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.dao.UserDaoMySqlImpl;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(LoginServlet.class);
    private int attempt;
    private long startTime;
    private DaoFactory daoFactory;
    private int LOGIN_ATTEMPT_QUANTITY = 3;
    private int WAIT_SECONDS = 15;

    public LoginServlet() throws ServletException, IOException {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder msgText = new StringBuilder();
        logger.debug("LoginServlet: =================enter========================");
        boolean showLoginForm = true;
        int cartSize = 0;

        CartDao cartDao = daoFactory.getCartDao();
        UserDao userDao = daoFactory.getUserDao();

        String viewToGo = "WEB-INF/view/login.jsp";
        HttpSession session = req.getSession();
        String log = req.getParameter("login");
        String pass = req.getParameter("password");
        logger.debug("LoginServlet: session==null ? " + (session == null));
        logger.debug("LoginServlet: user entered log = " + log);
        logger.debug("LoginServlet: user entered log = " + log);
        logger.debug("LoginServlet: user entered pass = " + pass);
        msgText.append("<center>");

        if (session != null) {
            logger.debug("LoginServlet: session != null");
            session.setAttribute("session", session);
            attempt = (session.getAttribute("attempt") == null) ? 0 : (int) session.getAttribute("attempt");

            // already logged in
            User currentUser = (User) session.getAttribute("user");
            // be sure that username is correct
            logger.debug("LoginServlet: currentUser: " + (currentUser == null ? "" : currentUser));

            if (currentUser != null) {
                if (session.getAttribute("cartSize") == null) {
                    Cart cart = cartDao.getCart(currentUser.getId());
                    cartSize = cart.getProducts().values().stream().reduce(0, (a, b) -> a + b);
                    session.setAttribute("cartSize", cartSize);
                }

                logger.debug("LoginServlet: user already logged in: " + currentUser);
                session.setAttribute("userName", currentUser.getName());
                showLoginForm = false;
                if (cartSize > 0) {
                    viewToGo = "./cart";
                }
            } // not logged in yet
            else {
                logger.debug("LoginServlet: user didn't log in yet");
                boolean accessGranted = false;
                long waitTime = 0;

                if ((log != null) && !("".equals(log))) {
                    logger.debug("LoginServlet: log != null");

                    currentUser = userDao.getUserByLogin(log);
                    boolean exist = (currentUser != null);
                    logger.debug("LoginServlet: user is present in DB = " + exist);

                    if (exist) {
                        String passVerif = UserDaoMySqlImpl.hashString(pass);

                        logger.debug("LoginServlet: currentUser = " + currentUser);
                        logger.debug("LoginServlet: passVerif = " + passVerif);
                        accessGranted = (currentUser.getPassword().equals(passVerif));
                        logger.debug("LoginServlet: accessGranted = " + accessGranted);
                        showLoginForm = !accessGranted && attempt < LOGIN_ATTEMPT_QUANTITY;
                        logger.debug("LoginServlet: showLoginForm = " + showLoginForm);
                        if (accessGranted) {
                            attempt = 0;
                            showLoginForm = false;
                            session.setAttribute("user", currentUser);
                            session.setAttribute("userName", currentUser.getName());
                            logger.debug("LoginServlet: user.getName() = " + currentUser.getName());

                            if (session.getAttribute("cartSize") == null) {
                                Cart cart = cartDao.getCart(currentUser.getId());
                                cartSize = cart.getProducts().values().stream().reduce(0, (a, b) -> a + b);
                                session.setAttribute("cartSize", cartSize);
                            }
                            if (cartSize > 0) {
                                viewToGo = "./cart";
                            }
                        }
                        else {
                            attempt++;
                            if (attempt >= LOGIN_ATTEMPT_QUANTITY) {
                                if (attempt == LOGIN_ATTEMPT_QUANTITY) {
                                    startTime = System.currentTimeMillis();
                                }
                                waitTime = WAIT_SECONDS - (System.currentTimeMillis() - startTime) / 1000;
                                if (waitTime > 0) {
                                    msgText.append("<br><font size=4 color='red'><b> Форма будет снова доступна через " + waitTime + " секунд</b></font>");
                                    showLoginForm = false;
                                }
                                else {
                                    attempt = 0;
                                    showLoginForm = true;
                                }
                            }
                            else if (attempt >= 0) {
                                msgText.append("<b><font size=4 color='red'>Неправильный пароль, попробуйте еще раз! (попытка #" + attempt + ")</font>");
                            }
                        }
                    }
                    else {
                        attempt = 0;
                        showLoginForm = false;
                        msgText.append("<br><b><font size=3 color='green'><center>Пользователь с таким логином еще не был зарегистрирован.</b>");
                        msgText.append("<br><b>Вы можете <a href='registration'>зарегестрироваться по ссылке</a></b></font>");
                    }
                }
                else {
                    attempt = 0;
                }
            }

        }
        msgText.append("</center>");
        logger.debug("LoginServlet: FORWARD to view = " + viewToGo);
        session.setAttribute("showLoginForm", showLoginForm);
        session.setAttribute("message", msgText.toString());
        session.setAttribute("attempt", attempt);

        daoFactory.deleteCartDao(cartDao);
        daoFactory.deleteUserDao(userDao);

        if (viewToGo.equals("./cart")){
            resp.sendRedirect(viewToGo);
        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher(viewToGo);
            rd.forward(req, resp);
        }
        logger.debug("LoginServlet: =================exit========================");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }

}
