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
        CartDao cartDao = daoFactory.getCartDao();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            session.setAttribute("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

        }
        else {
            int userId = currentUser.getId();
            session.setAttribute("userId", userId);
            logger.debug("CartServlet: Current user: " + currentUser.getName());
//            if (session.getAttribute("cart") == null) {
//                cart = cartDao.getCart(userId);
//                if (cart == null) {
//                    cartDao.createCart(userId);
//                }
//            }

            if (session.getAttribute("addPurchase") != null) {
                String[] selectedProduct = (session.getAttribute("addPurchaseId")).toString().split(":");

                for (String s : selectedProduct) {
                    int selectedProductId =  Integer.valueOf(selectedProduct[0]);
                    int newQuantity =  Integer.valueOf(selectedProduct[1]);
                    logger.debug("CartServlet: for user: " + currentUser.getName() + "was added " + newQuantity + " of productId " + selectedProductId);
                    cartDao.addProduct(currentUser.getId(), selectedProductId, newQuantity);
                }
            }

            if (session.getAttribute("removePurchase") != null) {
                String[] selectedProduct = (session.getAttribute("removePurchaseId")).toString().split(":");
                for (String s : selectedProduct) {
                    cartDao.removeProduct(currentUser.getId(), Integer.valueOf(selectedProduct[0]), Integer.valueOf(selectedProduct[1]));
                }
            }

//            cart = cartDao.getCart(userId);
//            logger.debug("CartServlet: Current user's cart is: " + cart);
//            session.setAttribute("cart", cart);

            logger.debug("CartServlet: call  cartDao.getProductsInCart(userId=" + userId +")");
            Map<Product, Integer> productsInCart = cartDao.getProductsInCart(userId);
            logger.debug("CartServlet: Products in the cart are: ");
            for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                logger.debug(product + " : " + quantity);
            }
            session.setAttribute("productsInCart", productsInCart);

            logger.debug("CartServlet: cartDao.getSize(): " + cartDao.getSize());
            session.setAttribute("cartSize", cartDao.getSize());
            logger.debug("CartServlet: cartDao.getTotalSum(): " + cartDao.getTotalSum());
            session.setAttribute("totalSum", cartDao.getTotalSum());
            session.setAttribute("productsIds", productsInCart.keySet().toArray());
        }

        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req, resp);
//        resp.sendRedirect(req.getContextPath() + "/cart");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
