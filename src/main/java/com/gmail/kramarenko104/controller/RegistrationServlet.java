package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.UserDao;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistrationServlet.class);
    private DaoFactory daoFactory;

    public RegistrationServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/view/registration.jsp");
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        StringBuilder message = new StringBuilder();
        boolean needRegistration = false;

        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String repass = request.getParameter("repassword");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String comment = request.getParameter("comment");

        Map<String, String> regData = new HashMap<>();
        regData.put("login", login);
        regData.put("pass", pass);
        regData.put("repass", repass);
        regData.put("name", name);
        regData.put("address", address);

        Map<String, String> errors = new HashMap<>();

        if (session != null) {
            // already logged in
            if (session.getAttribute("user") != null) {
                logger.debug("RegisrtServlet: some user is already logged in");
                User currentUser = (User) session.getAttribute("user");
                String currentLogin = (currentUser).getLogin();
                if (currentLogin.equals(login)) {
                    logger.debug("RegisrtServlet: logged in user tries to registere. Just forward him to page with products");
                    message.append("<br><b><font color='green'><center>Hi, " + currentUser.getName() + ". <br>You are registered already.</font><b>");
                } else { // we should logout and login as the new user
                    logger.debug("RegisrtServlet: try to register when user is logged in. Then logout and forward to registartion.jsp");
                    session.invalidate();
                    session = request.getSession();
                    needRegistration = true;
                }
            } else {// not logged in yet
                logger.debug("RegisrtServlet: no one user is logged in. Just check entered fields from registration form");

                for (Map.Entry<String,String> entry: regData.entrySet()){
                    if (entry.getValue().length() < 1){
                        errors.put(entry.getKey(), "Cannot be empty!");
                    }
                }
                regData = null;

                if (repass.length() > 0 && !pass.equals(repass)) {
                    errors.put("regRepassword", "Password and retyped one don't match!");
                }

                String patternString = "([0-9a-zA-Z]+){4,}";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(pass);
                if (pass.length() > 0 && !matcher.matches()) {
                    errors.put("regPassword","Password should has minimum 4 symbols with at least one upper case letter and 1 digit!");
                }

                if (errors.size() == 0) {
                    UserDao userDao = daoFactory.getUserDao();
                    User newUser = new User();
                    newUser.setLogin(login);
                    newUser.setPassword(pass);
                    newUser.setAddress(address);
                    newUser.setComment(comment);
                    if (userDao.createUser(newUser)) {
                        message.append("<br><font color='green'><center>Hi, " + name + "! <br>You are registered now.</font>");
                        session.setAttribute("user", newUser);
                        session.setAttribute("userName", newUser.getName());
                    } else {
                        needRegistration = true;
                        message.append("<br><font color='red'><center>User wan't registered because of DB problems</font>");
                    }
                    daoFactory.deleteUserDao(userDao);
                } else {
                    needRegistration = true;
                    session.setAttribute("regErrors", errors);
                }
            }

            if (needRegistration){
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/registartion.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("WEB-INF/views/products.jsp");
            }
            session.setAttribute("message", message.toString());
        }



}

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }

}
