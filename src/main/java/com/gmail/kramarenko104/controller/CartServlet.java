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
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(CartServlet.class);
    private DaoFactory daoFactory;

    public CartServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            session.setAttribute("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

        }
        else {
            CartDao cartDao = daoFactory.getCartDao();

            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            if (session.getAttribute("cartSize") == null) {
                Cart cart = cartDao.getCart(currentUser.getId());
                int cartSize = cart.getProducts().values().stream().reduce(0, (a, b) -> a + b);
                session.setAttribute("cartSize", cartSize);
            }

            Map<Product, Integer> productsInCart = cartDao.getAllProducts(userId);
            session.setAttribute("productsInCart", productsInCart);
//            logger.debug("CartServlet: Products in the cart are: ");
//            for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
//                Product product = entry.getKey();
//                int quantity = entry.getValue();
//                logger.debug(product + " : " + quantity);
//            }
            session.setAttribute("productsIds", productsInCart.keySet().toArray());

            if (session.getAttribute("totalSum") == null) {
                int totalSum = 0;
                for (Map.Entry entry: productsInCart.entrySet()){
                    totalSum += (int)entry.getValue() * ((Product)entry.getKey()).getPrice();
                }
                session.setAttribute("totalSum", totalSum);
            }

            daoFactory.deleteCartDao(cartDao);
        }

        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req, resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            session.setAttribute("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

        }
        else {
            CartDao cartDao = daoFactory.getCartDao();

            Object addProducts = session.getAttribute("addPurchase");
            if (addProducts != null) {
                String[] addProductsArr = ((String)addProducts).split(":");
                logger.debug("CartServlet: got from form " + Arrays.asList(addProductsArr));
                for (String s : addProductsArr) {
                    int productId = Integer.valueOf(addProductsArr[0]);
                    int newQuantity = Integer.valueOf(addProductsArr[1]);
                    cartDao.addProduct(currentUser.getId(), productId, newQuantity);
                    logger.debug("CartServlet: for user: " + currentUser.getName() + "was added " + newQuantity + " of productId " + productId);
                }
            }

            Object rmProducts = session.getAttribute("removePurchase");
            if (rmProducts != null) {
                String[] rmProductsArr = ((String)rmProducts).split(":");
                for (String s : rmProductsArr) {
                    int productId = Integer.valueOf(rmProductsArr[0]);
                    int newQuantity = Integer.valueOf(rmProductsArr[1]);
                    cartDao.removeProduct(currentUser.getId(), productId, newQuantity);
                    logger.debug("CartServlet: for user: " + currentUser.getName() + "was removed " + newQuantity + " of productId " + productId);
                }
            }
            daoFactory.deleteCartDao(cartDao);
        }
        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
