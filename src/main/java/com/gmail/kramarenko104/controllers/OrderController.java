package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.dto.OrderDto;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.services.CartService;
import com.gmail.kramarenko104.services.OrderService;
import com.gmail.kramarenko104.services.UserService;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("/order")
@SessionAttributes(value = {"warning", "user", "cart", "order"})
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static final String DB_WARNING = "Check your connection to DB!";
    private OrderService orderService;
    private CartService cartService;
    private UserService userService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public OrderController(UserService userService,
                           CartService cartService,
                           OrderService orderService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(@ModelAttribute(name = "user") User user) {
        ModelAndView modelAndView = new ModelAndView("order");
        Cart cart = cartService.getCartByUserId(user.getUserId());
        Order order = orderService.getLastOrderByUserId(user.getUserId());
        modelAndView.addObject("user", user);
        modelAndView.addObject("cart", cart);
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String doPost(@RequestParam("action") String action,
                  @RequestParam("userId") long userId) {
        String jsondata = null;
        ModelAndView modelAndView = new ModelAndView("order");
        logger.debug("[eshop] OrderController.POST: .......enter......CREATE NEW ORDER .............");

        if (em != null) {
            User dbUser = userService.getUser(userId);
            if (dbUser != null) {
                modelAndView.addObject("user", dbUser);

                // getProduct info from Ajax POST request (updateCart.js)
                if (action != null && (action.equals("makeOrder"))) {
                    logger.debug("[eshop] OrderController.POST: got userId from POST request: " + userId);

                    // any user can have only one existing now cart and many processed orders (userId uniquely identifies cart)
                    Cart cart = cartService.getCartByUserId(userId);
                    logger.debug("[eshop] OrderController.POST: got cart from DB: " + cart);

                    // order will be created based on the cart's content
                    Order newOrder = orderService.createOrder(userId, cart.getProducts());

                    // transfer DTO Order (not entire complex Order) to view
                    OrderDto jsonOrder = new OrderDto(userId);
                    jsonOrder.setOrderNumber(newOrder.getOrderNumber());
                    jsonOrder.setProducts(newOrder.getProducts());
                    jsonOrder.setItemsCount(newOrder.getItemsCount());
                    jsonOrder.setTotalSum(newOrder.getTotalSum());
                    logger.debug("[eshop] OrderController.POST: !!! new Order was created: " + jsonOrder);
                    modelAndView.addObject("order", newOrder);

                    // send JSON back with the new Order to show on order.jsp
                    if (jsonOrder != null) {
                        jsondata = new GsonBuilder().setPrettyPrinting().create().toJson(jsonOrder);
                        logger.debug("[eshop] OrderController.POST: send JSON data to cart.jsp ---->" + jsondata);
                    }
                    logger.debug("[eshop] OrderController.POST: clearCartByUserId...");
                    cartService.clearCartByUserId(userId);
                    modelAndView.addObject("cart", null);
                }
            }
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        logger.debug("[eshop] >>>OrderController.POST:  exit .. cart: " + modelAndView.getModel().get("cart"));
        logger.debug("[eshop] >>>OrderController.POST:  exit .. order: " + modelAndView.getModel().get("order"));
        return jsondata;
    }
}
