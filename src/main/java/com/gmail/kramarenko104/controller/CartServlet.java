package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;


@Controller
@RequestMapping("/cart")
public class CartServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(CartServlet.class);
    private DaoFactory daoFactory;

    public CartServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        daoFactory.openConnection();

        if (session.getAttribute("user") != null) {
            User currentUser = (User) session.getAttribute("user");
            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            Cart userCart = null;
            if (session.getAttribute("userCart") == null) {
                CartDao cartDao = daoFactory.getCartDao();
                userCart = cartDao.getCart(userId);
                if (userCart == null) {
                    logger.debug("CartServlet: cart from DB == null! create new cart for userId: " + userId);
                    userCart = new Cart(userId);
                }
                session.setAttribute("userCart", userCart);
                daoFactory.deleteCartDao(cartDao);
            }
        }
        daoFactory.closeConnection();
        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        daoFactory.openConnection();
        boolean needRefresh = false;

        if (session.getAttribute("user") != null) {
            User currentUser = (User) session.getAttribute("user");
            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            // CHANGE CART
            CartDao cartDao = daoFactory.getCartDao();
            // get info from Ajax POST req (from updateCart.js)
            String param = req.getParameter("action");
            if (param != null && param.length() > 0) {
                int productId = 0;
                int quantity = 0;
                switch (param) {
                    case "add":
                        logger.debug("CatServlet: GOT PARAMETER 'add'....");
                        productId = Integer.valueOf(req.getParameter("productId"));
                        quantity = Integer.valueOf(req.getParameter("quantity"));
                        logger.debug("CatServlet: userId: " + currentUser.getId() + ", productId: "+ productId + ", quantity: " + quantity);
                        cartDao.addProduct(currentUser.getId(), productId, quantity);
                        logger.debug("CartServlet: for user '" + currentUser.getName() + "' was added " + quantity + " of productId: " + productId);
                        break;
                    case "remove":
                        logger.debug("CartServlet: GOT PARAMETER 'remove' ");
                        productId = Integer.valueOf(req.getParameter("productId"));
                        quantity = Integer.valueOf(req.getParameter("quantity"));
                        cartDao.removeProduct(currentUser.getId(), productId, quantity);
                        logger.debug("CartServlet: for user: " + currentUser.getName() + "was removed " + quantity + " of productId " + productId);
                        break;
                }
                needRefresh = true;
            }
            //  REFRESH CART's characteristics if refresh need
            Cart userCart = null;
            if (session.getAttribute("userCart") == null || needRefresh) {
                userCart = cartDao.getCart(userId);
                if (userCart == null) {
                    logger.debug("CartServlet: cart from DB == null! create the new cart for userId: " + userId);
                    userCart = new Cart(userId);
                }
                session.setAttribute("userCart", userCart);

                // send JSON with updated Cart back to cart.jsp
                if (userCart != null) {
                    String jsondata = new Gson().toJson(userCart);
                    logger.debug("CartServlet: send JSON data to cart.jsp ---->" + jsondata);
                    try(PrintWriter out = resp.getWriter()) {
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        out.print(jsondata);
                        out.flush();
                    }
                }
            }
            daoFactory.deleteCartDao(cartDao);
        }
        daoFactory.closeConnection();
    }
}
