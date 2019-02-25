package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(OrderServlet.class);
    private DaoFactory daoFactory;

    public OrderServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart;
        CartDao cartDao = daoFactory.getCartDao();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            session.setAttribute("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

        }
        else {
            int currentUserId = currentUser.getId();
            logger.debug("OrderServlet: currentUserId = " + currentUserId);
            int userIdFromJSP = Integer.valueOf((String)req.getAttribute("orderUserID"));
            logger.debug("OrderServlet: GOT from cart.jsp: orderUserID = " + userIdFromJSP);
//            cartDao.deleteCart(userIdFromJSP);
//            session.setAttribute("productsInCart", null);
        }

        req.getRequestDispatcher("WEB-INF/view/order.jsp").forward(req, resp);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
