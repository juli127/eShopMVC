package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartServiceImpl;
import com.gmail.kramarenko104.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Controller
@SessionAttributes(value = {"user", "cart", "selectedCateg", "products", "warning", "showLoginForm", "order"})
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private EntityManagerFactory emf;

    @RequestMapping(value = {"/", "/product"}, method = RequestMethod.GET)
    protected ModelAndView getProducts(@ModelAttribute(name = "user") User user,
                                       @RequestParam(value = "selectedCategory", required = false) String selectedCateg) {

        ModelAndView modelAndView = new ModelAndView("products");
        logger.debug("ProductController.doGet:   enter.. currentUser: " + user);

        // connection to DB is open
        if (emf != null) {
            List<Product> products;

            // when form is opened at the first time, selectedCateg == null
            if (selectedCateg != null) {
                products = productService.getProductsByCategory(Integer.parseInt(selectedCateg));
            } else {
                products = productService.getAllProducts();
            }
            modelAndView.addObject("products", products);
//            products.forEach(e -> logger.debug(e.toString()));

            // be sure that when we enter on the main application page (products.jsp), user's info is full and correct
            if (user == null || (user != null && user.getLogin() == null)) {
                logger.debug("ProductController.doGet:   user==null,so.. null all attributes");
                modelAndView.addObject("order", null);
                modelAndView.addObject("cart", null);
                modelAndView.addObject("message", null);
                modelAndView.addObject("attempt", null);
                modelAndView.addObject("showLoginForm", true);
            } else {
                //re-check user cart according to currentUser
                logger.debug("ProductController.doGet:   user!=null,so.. get Cart from db...");
                long userId = user.getUserId();
                Cart userCart = cartService.getCartByUserId(userId);
                logger.debug("ProductController.GET:  got from db userCart " + userCart);
                modelAndView.addObject("cart", userCart);
            }
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @ModelAttribute("user")
    public User populateUser() {
        return new User();
    }
}
