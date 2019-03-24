package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private DaoFactory daoFactory;
    private static Logger logger = Logger.getLogger(CartController.class);

    public CartController()
    {
        //daoFactory = DaoFactory.getSpecificDao();
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet() {
        HttpSession session = session();
        daoFactory.openConnection();

        if (session.getAttribute("user") != null) {
            User currentUser = (User) session.getAttribute("user");
            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            Cart userCart = null;
            if (session.getAttribute("userCart") == null) {
                CartService cartService = daoFactory.getCartService();
                userCart = cartService.getCart(userId);
                if (userCart == null) {
                    logger.debug("CartServlet: cart from DB == null! create new cart for userId: " + userId);
                    userCart = new Cart(userId);
                }
                session.setAttribute("userCart", userCart);
                daoFactory.deleteCartService(cartService);
            }
        }
        daoFactory.closeConnection();
        ModelAndView model = new ModelAndView("WEB-INF/view/cart.jsp");
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = session();
        daoFactory.openConnection();
        boolean needRefresh = false;

        if (session.getAttribute("user") != null) {
            User currentUser = (User) session.getAttribute("user");
            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            // CHANGE CART
            CartService cartService = daoFactory.getCartService();
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
                        cartService.addProduct(currentUser.getId(), productId, quantity);
                        logger.debug("CartServlet: for user '" + currentUser.getName() + "' was added " + quantity + " of productId: " + productId);
                        break;
                    case "remove":
                        logger.debug("CartServlet: GOT PARAMETER 'remove' ");
                        productId = Integer.valueOf(req.getParameter("productId"));
                        quantity = Integer.valueOf(req.getParameter("quantity"));
                        cartService.removeProduct(currentUser.getId(), productId, quantity);
                        logger.debug("CartServlet: for user: " + currentUser.getName() + "was removed " + quantity + " of productId " + productId);
                        break;
                }
                needRefresh = true;
            }
            //  REFRESH CART's characteristics if refresh need
            Cart userCart = null;
            if (session.getAttribute("userCart") == null || needRefresh) {
                userCart = cartService.getCart(userId);
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
            daoFactory.deleteCartService(cartService);
        }
        daoFactory.closeConnection();
        ModelAndView model = new ModelAndView("WEB-INF/view/cart.jsp");
        return model;
    }
}