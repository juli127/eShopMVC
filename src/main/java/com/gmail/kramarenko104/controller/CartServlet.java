package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.debug("CartServlet: -------enter-------------------- ");
        HttpSession session = request.getSession();
        boolean needRefresh = false;

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            logger.debug("CartServlet: Current user == null ");
            session.setAttribute("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

            // be sure that all user's corresponding values are null, too
            session.setAttribute("cartSize", null);
            session.setAttribute("userName", null);
            session.setAttribute("totalSum", 0);
            session.setAttribute("productsInCart", null);
        }
        else {
            CartDao cartDao = daoFactory.getCartDao();

            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            //////////////////// CHANGE CART /////////////////////////////////////
            String addProducts = request.getParameter("addPurchase");
            if (addProducts != null) {
                logger.debug("CatServlet: GOT PARAMETER addPurchase: === " + addProducts);
                String[] addProductsArr = ((String)addProducts).split(":");
                for (String s : addProductsArr) {
                    int productId = Integer.valueOf(addProductsArr[0]);
                    int quantity = Integer.valueOf(addProductsArr[1]);
                    cartDao.addProduct(currentUser.getId(), productId, quantity);
                    logger.debug("CartServlet: for user: " + currentUser.getName() + "was added " + quantity + " of productId " + productId);
                }
                needRefresh = true;
            }

            String rmProducts = request.getParameter("removePurchase");
            if (rmProducts != null) {
                logger.debug("CartServlet: GOT PARAMETER removePurchase: === " + rmProducts);
                String[] rmProductsArr = ((String)rmProducts).split(":");
                for (String s : rmProductsArr) {
                    int productId = Integer.valueOf(rmProductsArr[0]);
                    int quantity = Integer.valueOf(rmProductsArr[1]);
                    cartDao.removeProduct(currentUser.getId(), productId, quantity);
                    logger.debug("CartServlet: for user: " + currentUser.getName() + "was removed " + quantity + " of productId " + productId);
                }
                needRefresh = true;
            }

            ///////////////// REFRESH CART's characteristics if refresh need ////////////////////////////////////////
            logger.debug("CartServlet: needRefresh ==  "+ needRefresh);

            if (session.getAttribute("cartSize") == null || needRefresh) {
                Cart cart = cartDao.getCart(currentUser.getId());
                int cartSize = cart.getProducts().values().stream().reduce(0, (a, b) -> a + b);
                session.setAttribute("cartSize", cartSize);
                logger.debug("CartServlet: Refresh  cart size==  "+ cartSize);
            }

            Map<Product, Integer> productsInCart = null;
            if (session.getAttribute("productsInCart") == null || needRefresh) {
                productsInCart = cartDao.getAllProducts(userId);
                session.setAttribute("productsInCart", productsInCart);
                //logger.debug("CartServlet.doGet: Refresh  productsInCart==  "+ productsInCart);
                session.setAttribute("productsIds", productsInCart.keySet().toArray());
            }

            if (session.getAttribute("totalSum") == null  || needRefresh) {
                int totalSum = 0;
                for (Map.Entry entry: productsInCart.entrySet()){
                    totalSum += (int)entry.getValue() * ((Product)entry.getKey()).getPrice();
                }
                session.setAttribute("totalSum", totalSum);
                logger.debug("CartServlet.doGet: >>>>>> Refresh  totalSum==  "+ totalSum);
            }

            session.setAttribute("user", currentUser);

            daoFactory.deleteCartDao(cartDao);
        }

        logger.debug("CartServlet: needRefresh ==  "+ needRefresh);

        logger.debug("CartServlet.doGet: >>>>>> where session.getAttribute(cartSize)= " + session.getAttribute("cartSize"));
        logger.debug("CartServlet.doGet: >>>>>> where session.getAttribute(totalSum)= " + session.getAttribute("totalSum"));


            if (needRefresh || currentUser == null) {
                logger.debug("CartServlet.doGet: >>>>>> sendRedirect to /cart........... ");
                response.sendRedirect(request.getContextPath() + "/cart");
            }
            else {
                logger.debug("CartServlet.doGet: >>>>>> call forward to cart.jsp........... ");
                request.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(request, response);
            }

        logger.debug("CartServlet.doGet: -------exit-------------------- ");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
