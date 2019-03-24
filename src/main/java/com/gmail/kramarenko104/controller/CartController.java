package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private DaoFactory daoFactory;
    private static Logger logger = Logger.getLogger(CartController.class);

    public CartController() {}

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(Model model) {

        daoFactory.openConnection();
        if (model.containsAttribute("user")) {
            User currentUser = (User) model.asMap().get("user");
            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            Cart userCart = null;
            if (model.asMap().get("userCart") == null) {
                CartService cartService = daoFactory.getCartService();
                userCart = cartService.getCart(userId);
                if (userCart == null) {
                    logger.debug("CartServlet: cart from DB == null! create new cart for userId: " + userId);
                    userCart = new Cart(userId);
                }
                model.addAttribute("userCart", userCart);
                daoFactory.deleteCartService(cartService);
            }
        }
        daoFactory.closeConnection();
        return "cart";
    }


    @RequestMapping(method = RequestMethod.POST, produces="text/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    protected String doPost(HttpServletRequest req, Model model) {
        daoFactory.openConnection();
        boolean needRefresh = false;
        String jsonResp = null;

        if (model.containsAttribute("user")) {
            User currentUser = (User) model.asMap().get("user");
            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser==null ? 0: currentUser.getId();

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
            if (model.asMap().get("userCart") == null || needRefresh) {
                userCart = cartService.getCart(userId);
                if (userCart == null) {
                    logger.debug("CartServlet: cart from DB == null! create the new cart for userId: " + userId);
                    userCart = new Cart(userId);
                }
                model.addAttribute("userCart", userCart);

                // send JSON with updated Cart back to cart.jsp
                if (userCart != null) {
                    String jsondata = new Gson().toJson(userCart);
                    logger.debug("CartServlet: send JSON data to cart.jsp ---->" + jsondata);
                }
            }
            daoFactory.deleteCartService(cartService);
        }
        daoFactory.closeConnection();
        return jsonResp;
    }
}
