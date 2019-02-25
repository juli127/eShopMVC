package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.dao.UserDaoMySqlImpl;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
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

    public LoginServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder msgText = new StringBuilder();
        boolean showLoginForm = true;
        String viewToGo = "WEB-INF/view/login.jsp";
        HttpSession session = req.getSession();
        String log = req.getParameter("login");
        String pass = req.getParameter("password");
        logger.debug("LoginServlet: session==null ? " + (session == null));
        logger.debug("LoginServlet: user entered on LOGIN form log = " + log);
        logger.debug("LoginServlet: user entered on LOGIN form pass = " + pass);
        msgText.append("<center>");

        if (session != null) {
            logger.debug("LoginServlet: session != null");
            session.setAttribute("session", session);
            Object attemptFromSession = session.getAttribute("attempt");
            attempt = (attemptFromSession == null) ? 0 : (int) attemptFromSession;
            // already logged in
            User currentUser = (User) session.getAttribute("user");
            // be sure that username is correct
            logger.debug("LoginServlet: currentUser: " + (currentUser == null ? "" : currentUser));

            if (currentUser != null) {
                logger.debug("LoginServlet: user already logged in: " + currentUser);
                session.setAttribute("userName", currentUser.getName());
                showLoginForm = false;
                viewToGo = "./cart";
            } // not logged in yet
            else {
                logger.debug("LoginServlet: nobody not logged in yet");
                boolean accessGranted = false;
                long waitTime = 0;

                if (log != null) {
                    logger.debug("LoginServlet: log != null");
                    UserDao userDao = daoFactory.getUserDao();
                    User userDB = userDao.getUserByLogin(log);
                    boolean exist = (userDB != null);
                    logger.debug("LoginServlet: user is present in DB = " + exist);
                    if (exist) {
                        String passVerif = UserDaoMySqlImpl.hashString(pass);
                        accessGranted = (userDB.getPassword().equals(passVerif));
                        logger.debug("LoginServlet: accessGranted = " + accessGranted);
                        showLoginForm = !accessGranted && attempt < LOGIN_ATTEMPT_QUANTITY;
                        logger.debug("LoginServlet: showLoginForm = " + showLoginForm);
                        if (accessGranted) {
                            attempt = 0;
                            showLoginForm = false;
                            session.setAttribute("user", userDB);
                            session.setAttribute("userName", userDB.getName());
                            logger.debug("LoginServlet: user.getName() = " + userDB.getName());
                            viewToGo = "./cart";
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

        if (viewToGo.equals("./cart")){
            resp.sendRedirect(viewToGo);
        }
        if (viewToGo.equals("WEB-INF/view/login.jsp")){
            RequestDispatcher rd = req.getRequestDispatcher(viewToGo);
            rd.forward(req, resp);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }

}
