package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.service.CartService;
import com.gmail.kramarenko104.service.OrderService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private DaoFactory daoFactory;

    public OrderController() {
        //daoFactory = DaoFactory.getSpecificDao();
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet() {
        return new ModelAndView("WEB-INF/view/order.jsp");
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = session();
        ModelAndView model = new ModelAndView("order");
        daoFactory.openConnection();
        boolean needRefresh = false;

        if (session.getAttribute("user") != null) {
            // get info from Ajax POST request (updateCart.js)
            String param = req.getParameter("action");
            if (param != null && (param.equals("makeOrder"))) {
                int userId = Integer.valueOf(req.getParameter("userId"));
                logger.debug("OrderServlet.POST: got userId from POST request: " + userId);

                // any user can have only one existing now cart and many processed orders (userId uniquely identifies cart)
                CartService cartService = daoFactory.getCartService();
                Cart cart = cartService.getCart(userId);
                logger.debug("OrderServlet.POST: got cart from DB: " + cart);

                // order will be created based on the cart's content
                OrderService orderService = daoFactory.getOrderService();
                Order newOrder = orderService.createOrder(userId, cart.getProducts());
                logger.debug("OrderServlet.POST: !!! new Order was created: " + newOrder);
                model.addObject("newOrder", newOrder);

                // send JSON back with the new Order to show on order.jsp
                if (newOrder != null) {
                    String jsondata = new Gson().toJson(newOrder);
                    logger.debug("OrderServlet: send JSON data to cart.jsp ---->" + jsondata);
                    try(PrintWriter out = resp.getWriter()) {
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        out.print(jsondata);
                        out.flush();
                    }
                }
                cartService.deleteCart(Integer.valueOf(userId));
                session.setAttribute("userCart", null);
                daoFactory.deleteCartService(cartService);
                daoFactory.deleteOrderService(orderService);
            }
        }
        daoFactory.closeConnection();
        return model;
    }
}
