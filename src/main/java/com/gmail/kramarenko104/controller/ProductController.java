package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.service.CartDao;
import com.gmail.kramarenko104.service.ProductDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
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
import java.util.List;

@Controller
@RequestMapping({"/", "/products"})
public class ProductController {

    private static Logger logger = Logger.getLogger(ProductController.class);
    @Autowired
    private DaoFactory daoFactory;

    public ProductController()
    {
        logger.debug("---enter ProductController.ProductController...");
        //daoFactory = DaoFactory.getSpecificDao();
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = session();
        ModelAndView model = new ModelAndView("/WEB-INF/view/products.jsp");
        logger.debug("---enter ProductController.doGet...");
        daoFactory.openConnection();

        // prepare products list depending on selected category
        ProductDao productDao = daoFactory.getProductService();
        String selectedCateg = req.getParameter("selectedCategory");
        List<Product> products;

        // when form is opened at the first time, selectedCateg == null
        if (selectedCateg != null) {
            products = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
        } else {
            products = productDao.getAllProducts();
        }
        model.addObject("selectedCateg", selectedCateg);
        model.addObject("products", products);
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
                CartDao cartDao = daoFactory.getCartService();
                userCart = cartDao.getCart(userId);
                if (userCart == null) {
                    userCart = new Cart(userId);
                }
                session.setAttribute("userCart", userCart);
                daoFactory.deleteCartDao(cartDao);
            }
        }
        daoFactory.closeConnection();
        return model;
    }
}
