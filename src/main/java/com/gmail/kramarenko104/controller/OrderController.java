package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.service.CartServiceImpl;
import com.gmail.kramarenko104.service.OrderServiceImpl;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger logger = Logger.getLogger(OrderController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private CartServiceImpl cartService;

    public OrderController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet() {
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/json")
    public String doPost(@RequestParam("action") String action,
                         @RequestParam("userId") int userId,
                         Model model) {

        String jsondata = null;

        if (cartService.sessionIsOpen()) {
            if (model.asMap().get("user") != null) {
                // get info from Ajax POST request (updateCart.js)
                if (action != null && (action.equals("makeOrder"))) {
                    logger.debug("OrderServlet.POST: got userId from POST request: " + userId);

                    // any user can have only one existing now cart and many processed orders (userId uniquely identifies cart)
                    Cart cart = cartService.getCart(userId);
                    logger.debug("OrderServlet.POST: got cart from DB: " + cart);

                    // order will be created based on the cart's content
                    Order newOrder = orderService.createOrder(userId, cart.getProducts());
                    logger.debug("OrderServlet.POST: !!! new Order was created: " + newOrder);
                    model.addAttribute("newOrder", newOrder);

                    // send JSON back with the new Order to show on order.jsp
                    if (newOrder != null) {
                        jsondata = new Gson().toJson(newOrder);
                        logger.debug("OrderServlet: send JSON data to cart.jsp ---->" + jsondata);
                    }
                    cartService.deleteCart(Integer.valueOf(userId));
                    model.addAttribute("userCart", null);
                }
            }
        } else { // connection to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return jsondata;
    }
}
