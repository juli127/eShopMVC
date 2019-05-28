package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dto.CartDto;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartServiceImpl;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManagerFactory;

@Controller
@SessionAttributes(value = {"warning", "user"})
@RequestMapping("/cart")
public class CartController {

    private static Logger logger = LoggerFactory.getLogger(CartController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private EntityManagerFactory emf;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(ModelAndView modelAndView,
                              @ModelAttribute(name = "user") User user) {
        modelAndView.setViewName("cart");
        if (emf != null) {
            Cart cart = cartService.getCartByUserId(user.getUserId());
            modelAndView.addObject("cart", cart);
            System.out.println(">>>>CartController.doGet:   enter.. cart from DB: " + cart);
            modelAndView.addObject("user", user);
        } else {
            modelAndView.addObject("warning", DB_WARNING);
        }
        System.out.println(">>>>CartController.doGet:   exit.. userCart: " + modelAndView.getModel().get("cart"));
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String doPost(ModelAndView modelAndView,
                  @RequestParam("action") String action,
                  @RequestParam("productId") int productId,
                  @RequestParam("quantity") int quantity,
                  @ModelAttribute(name = "user") User user) {

        String jsonString = null;
        modelAndView.setViewName("cart");
        System.out.println("CartController.doPost: enter with productId: " + productId + ", quantity:" + quantity +"----------------------------");

        if (emf != null) {
            if (user != null) {
                long userId = user.getUserId();
                modelAndView.addObject("user", user);

                // CHANGE CART
                // getProduct info from Ajax POST req (from updateCart.js)
                boolean needRefresh = false;
                if (action != null && action.length() > 0) {
                    switch (action) {
                        case "add":
                            System.out.println("CartController.doPost: GOT PARAMETER 'add'....");
                            cartService.addProduct(user.getUserId(), productId, quantity);
                            System.out.println("CartController.doPost: for user '" + user.getName() + "' was added " + quantity + " of productId: " + productId);
                            break;
                        case "remove":
                            System.out.println("CartController.doPost: GOT PARAMETER 'remove' ");
                            cartService.removeProduct(user.getUserId(), productId, quantity);
                            System.out.println("CartController.doPost: for user: " + user.getUserId() + " was removed " + quantity + " of productId " + productId);
                            break;
                    }
                    needRefresh = true;
                }
                //  REFRESH CART's characteristics if need to refresh
                if (needRefresh) {
                    Cart userCart = cartService.getCartByUserId(userId);
                    System.out.println("CartController.doPost:  updated cart " + userCart);
                    modelAndView.addObject("cart", userCart);

                    // send JSON with updated Cart back to cart.jsp
                    if (userCart != null) {
                        // we don't need to pass full Cart object with included User object, but only cart's properties
                        // so, use Cart DTO object for marshalling
                        CartDto jsonCart = new CartDto(userId);
                        jsonCart.setProducts(userCart.getProducts());
                        jsonCart.setItemsCount(userCart.getItemsCount());
                        jsonCart.setTotalSum(userCart.getTotalSum());
                        jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(jsonCart);
                    }
                }
            }
        } else { // session to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
//        System.out.println("JULIA: CartController.doPost:  return json: " + jsonString);
        System.out.println("CartController.doPost:   exit.. cart: " + modelAndView.getModel().get("cart"));
        return jsonString;
    }
}
