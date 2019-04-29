package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartServiceImpl;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static Logger logger = Logger.getLogger(CartController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    @Autowired
    private CartServiceImpl cartService;

    public CartController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet() {
        ModelAndView modelAndView = new ModelAndView("cart");
        if (cartService.sessionIsOpen()) {
            if (modelAndView.getModelMap().get("user") != null) {
                User currentUser = (User) modelAndView.getModelMap().get("user");
                logger.debug("CartServlet: Current user: " + currentUser.getName());
                int userId = currentUser.getId();

                if (modelAndView.getModelMap().get("userCart") == null) {
                    Cart userCart = cartService.getCart(userId);
                    modelAndView.addObject("userCart", userCart);
                }
            }
        } else {
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST, produces = "text/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String doPost(@RequestParam("action") String action,
                         @RequestParam("productId") int productId,
                         @RequestParam("quantity") int quantity,
                         Model model) {

        String jsonResp = null;

        if (cartService.sessionIsOpen()) {
            if (model.asMap().get("user") != null) {
                User currentUser = (User) model.asMap().get("user");
                logger.debug("CartServlet: Current user: " + currentUser.getName());
                int userId = currentUser.getId();

                // CHANGE CART
                // get info from Ajax POST req (from updateCart.js)
                boolean needRefresh = false;
                if (action != null && action.length() > 0) {
                    switch (action) {
                        case "add":
                            logger.debug("CatServlet: GOT PARAMETER 'add'....");
                            logger.debug("CatServlet: userId: " + currentUser.getId() + ", productId: " + productId + ", quantity: " + quantity);
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
                if (model.asMap().get("userCart") == null || needRefresh) {
                    Cart userCart = cartService.getCart(userId);
                    model.addAttribute("userCart", userCart);

                    // send JSON with updated Cart back to cart.jsp
                    String jsondata = new Gson().toJson(userCart);
                    logger.debug("CartServlet: send JSON data to cart.jsp ---->" + jsondata);
                }
            }
        } else { // session to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return jsonResp;
    }
}
