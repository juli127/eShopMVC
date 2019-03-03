package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/order")
public class OrderServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(OrderServlet.class);
    private DaoFactory daoFactory;

    public OrderServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

//    private static HttpSession getSession() {
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        return attr.getRequest().getSession(true);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {
//        HttpSession session = getSession();
        Cart cart;
        CartDao cartDao = daoFactory.getCartDao();
        User currentUser = (User) model.get("user");
        if (currentUser == null) {
            model.put("message", "<a href='login'>Login</a> to see your cart. <br> " +
                    "Or <a href='registration'>register</a>");

        }
        else {
            int currentUserId = currentUser.getId();
            logger.debug("OrderServlet: currentUserId = " + currentUserId);
            int userIdFromJSP = Integer.valueOf((String)model.get("orderUserID"));
            logger.debug("OrderServlet: GOT from cart.jsp: orderUserID = " + userIdFromJSP);
//            cartDao.deleteCart(userIdFromJSP);
//            session.setAttribute("productsInCart", null);
        }

        return "order";

    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(ModelMap model) {
        doGet(model);
    }

    // where to close connection???
    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
