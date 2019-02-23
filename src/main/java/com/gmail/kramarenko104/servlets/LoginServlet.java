package com.gmail.kramarenko104.servlets;

import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.models.User;
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

    public LoginServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder msgText = new StringBuilder();
        boolean showLoginForm = true;
    StringBuilder message = new StringBuilder();
        HttpSession session = request.getSession();
        String log = request.getParameter("login");
        String pass = request.getParameter("password");
        msgText.append("<center>");

        if (session != null) {
            Object attemptFromSession = session.getAttribute("attempt");
            attempt = ( attemptFromSession == null) ? 0 : (int) attemptFromSession;
            // already logged in
            User currentUser = (User) session.getAttribute("user");
            // be sure that username is correct

            if (currentUser != null) {
                session.setAttribute("userName", currentUser.getName());
                showLoginForm = false;
            } // not logged in yet
            else {
                boolean accessGranted = false;
                long waitTime = 0;

                if (log != null) {
                    UserDao userDao = daoFactory.getUserDao();
                    boolean exist = userDao.userExists(log);
                    message.append("exist = " + exist + "<br>");
                    if (exist) {
                        User user = userDao.getUserByLoginPass(log, pass);
                        accessGranted = ( user != null);
                        message.append("accessGranted = " + accessGranted + "<br>");
                        showLoginForm = ((attempt == 0) || (!accessGranted && attempt < 3));
                        message.append("showLoginForm = " + showLoginForm + "<br>");
                        if (accessGranted) {
                            attempt = 0;
                            showLoginForm = false;
                            session.setAttribute("user", user);
                            session.setAttribute("userName", user.getName());
                            message.append("user.getName() = " + user.getName() + "<br>");
                            getServletContext().setAttribute("session", session);
                            getServletContext().setAttribute("user", user);
                            getServletContext().setAttribute("userName", user.getName());
                        } else {
                            attempt++;
                            if (attempt >= 3) {
                                if (attempt == 3) {
                                    startTime = System.currentTimeMillis();
                                }
                                waitTime = 15 - (System.currentTimeMillis() - startTime) / 1000;
                                if (waitTime > 0) {
                                    msgText.append("<br><font size=4 color='red'><b> Форма будет снова доступна через " + waitTime + " секунд</b></font>");
                                    showLoginForm = false;
                                } else {
                                    attempt = 0;
                                    showLoginForm = true;
                                }
                            } else if (attempt >= 0) {
                                msgText.append("<b><font size=4 color='red'>Неправильный пароль, попробуйте еще раз! (попытка #" + attempt + ")</font>");
                            }
                        }
                    } else {
                        attempt = 0;
                        showLoginForm = false;
                        msgText.append("<br><b><font size=3 color='green'><center>Пользователь с таким логином еще не был зарегистрирован.</b>");
                        msgText.append("<br><b>Вы можете <a href='registration'>зарегестрироваться по ссылке</a></b></font>");
                    }
                    daoFactory.closeConnection();
                } else {
                    attempt = 0;
                }
            }

        }
        msgText.append("</center>");
        session.setAttribute("showLoginForm", showLoginForm);
//            session.setAttribute("message", msgText.toString());
        session.setAttribute("message1", message.toString());
        session.setAttribute("attempt", attempt);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/login.jsp");
        rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
