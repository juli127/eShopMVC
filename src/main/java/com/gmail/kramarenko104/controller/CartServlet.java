package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(CartServlet.class);
    private DaoFactory daoFactory;
    public CartServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message = "";

        HttpSession session = req.getSession();
        if (session == null) message += "session == null";
        else message += "session != null";

        getServletContext().setAttribute("messagecart", message);

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            session.setAttribute("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

        } else {
            if (session.getAttribute("cart") == null) {
                CartDao cartDao = daoFactory.getCartDao();
                Cart cart = cartDao.getCart(currentUser.getId());
                if (cart == null) {
                    cart = cartDao.createCart(currentUser.getId());
                }
                session.setAttribute("cart", cart);
                daoFactory.closeConnection();
            }
        }
        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req, resp);
//        resp.sendRedirect(req.getContextPath() + "/cart");

//        Product product = new Product();
//        product.setId(Integer.valueOf(req.getParameter("productID")));
//        cart.addProduct(product);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
