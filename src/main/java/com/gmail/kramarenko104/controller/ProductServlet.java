package com.gmail.kramarenko104.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.dao.ProductDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;
import com.gmail.kramarenko104.model.Product;
import javax.servlet.annotation.WebServlet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping({"/", "/products"})
@WebServlet(name = "ProductServlet", urlPatterns = {"/", "/products"})
public class ProductServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ProductServlet.class);
    private DaoFactory daoFactory;

    public ProductServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        daoFactory.openConnection();

        // prepare products list depending on selected category
        ProductDao productDao = daoFactory.getProductDao();
        String selectedCateg = req.getParameter("selectedCategory");
        List<Product> products;

        // when form is opened at the first time, selectedCateg == null
        if (selectedCateg != null) {
            products = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
        } else {
            products = productDao.getAllProducts();
        }
        session.setAttribute("selectedCateg", selectedCateg);
        session.setAttribute("products", products);
        // products.forEach(e -> logger.debug(e));
        daoFactory.deleteProductDao(productDao);

        // be sure that when we enter on the main application page (products.jsp), user's info is full and correct
        if (session.getAttribute("user") == null) {
            session.setAttribute("userCart", null);
        } else {
            User currentUser = (User) session.getAttribute("user");
            Cart userCart = null;
            if (session.getAttribute("userCart") == null) {
                int userId = currentUser.getId();
                CartDao cartDao = daoFactory.getCartDao();
                userCart = cartDao.getCart(userId);
                if (userCart == null) {
                    userCart = new Cart(userId);
                }
                session.setAttribute("userCart", userCart);
                daoFactory.deleteCartDao(cartDao);
            }
        }
        daoFactory.closeConnection();
        req.getRequestDispatcher("/WEB-INF/view/products.jsp").forward(req, resp);
    }
}
