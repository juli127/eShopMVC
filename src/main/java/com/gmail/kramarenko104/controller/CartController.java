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
import org.springframework.web.bind.annotation.*;


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
    protected String doPost(@RequestParam("action") String action,
                            @RequestParam("productId") int productId,
                            @RequestParam("quantity") int quantity,
                            Model model) {
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
            if (action != null && action.length() > 0) {
                switch (action) {
                    case "add":
                        logger.debug("CatServlet: GOT PARAMETER 'add'....");
                        logger.debug("CatServlet: userId: " + currentUser.getId() + ", productId: "+ productId + ", quantity: " + quantity);
                        cartService.addProduct(currentUser.getId(), productId, quantity);
                        logger.debug("CartServlet: for user '" + currentUser.getName() + "' was added " + quantity + " of productId: " + productId);
                        break;
                    case "remove":
                        logger.debug("CartServlet: GOT PARAMETER 'remove' ");
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
