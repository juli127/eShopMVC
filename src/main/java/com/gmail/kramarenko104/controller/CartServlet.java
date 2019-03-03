package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
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
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartServlet {

    private static Logger logger = Logger.getLogger(CartServlet.class);
    private DaoFactory daoFactory;

    public CartServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }


    private static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {

        logger.debug("CartServlet: -------enter-------------------- ");
//        HttpSession session = getSession();
        boolean needRefresh = false;

        User currentUser = (User) model.get("user");
        if (currentUser == null) {
            logger.debug("CartServlet: Current user == null ");
            model.put("message", "<a href='login'>Войдите в систему</a>, чтобы просмотреть свою корзину. <br> " +
                    "Или <a href='registration'>зарегистрируйтесь</a>");

            // be sure that all user's corresponding values are null, too
            model.put("cartSize", null);
            model.put("userName", null);
            model.put("totalSum", 0);
            model.put("productsInCart", null);
        }
        else {
            CartDao cartDao = daoFactory.getCartDao();

            logger.debug("CartServlet: Current user: " + currentUser.getName());
            int userId = currentUser.getId();

            //////////////////// CHANGE CART /////////////////////////////////////
            String addProducts = (String) model.get("addPurchase");
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

            String rmProducts = (String) model.get("removePurchase");
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

            if (model.get("cartSize") == null || needRefresh) {
                Cart cart = cartDao.getCart(currentUser.getId());
                int cartSize = cart.getProducts().values().stream().reduce(0, (a, b) -> a + b);
                model.put("cartSize", cartSize);
                logger.debug("CartServlet: Refresh  cart size==  "+ cartSize);
            }

            Map<Product, Integer> productsInCart = null;
            logger.debug("CartServlet: productsInCart attribute is null? " +
                    (model.get("productsInCart") == null));
            if (model.get("productsInCart") == null || needRefresh) {
                productsInCart = cartDao.getAllProducts(userId);
                model.put("productsInCart", productsInCart);
                //logger.debug("CartServlet.doGet: Refresh  productsInCart==  "+ productsInCart);
                //session.setAttribute("productsIds", productsInCart.keySet().toArray());
            }

            if (model.get("totalSum") == null  || needRefresh) {
                int totalSum = 0;
                for (Map.Entry entry: productsInCart.entrySet()){
                    totalSum += (int)entry.getValue() * ((Product)entry.getKey()).getPrice();
                }
                model.put("totalSum", totalSum);
                logger.debug("CartServlet.doGet: >>>>>> Refresh  totalSum ==  "+ totalSum);
            }

            model.put("user", currentUser);

            daoFactory.deleteCartDao(cartDao);
        }

        logger.debug("CartServlet: needRefresh ==  "+ needRefresh);
        logger.debug("CartServlet.doGet: >>>>>> where model.get(cartSize)= " + model.get("cartSize"));
        logger.debug("CartServlet.doGet: >>>>>> where model.get(totalSum)= " + model.get("totalSum"));

        logger.debug("CartServlet.doGet: >>>>>> call forward to cart.jsp........... ");
        logger.debug("CartServlet.doGet: -------exit-------------------- ");
        return "cart";


    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(ModelMap model) {
        doGet(model);
    }

    // where to close connection???
//    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }
}
