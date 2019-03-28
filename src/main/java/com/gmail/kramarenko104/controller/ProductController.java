package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.CartService;
import com.gmail.kramarenko104.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping({"/", "/products"})
public class ProductController {

    private static Logger logger = Logger.getLogger(ProductController.class);
    private static final String DB_WARNING = "Check your connection to DB!";

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    public ProductController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam("selectedCategory") String selectedCateg, Model model) {

        // connection to DB is open
        if (productService.sessionIsOpen()) {
            List<Product> products;

            // when form is opened at the first time, selectedCateg == null
            if (selectedCateg != null) {
                products = productService.getProductsByCategory(Integer.parseInt(selectedCateg));
            } else {
                products = productService.getAllProducts();
            }
            model.addAttribute("selectedCateg", selectedCateg);
            model.addAttribute("products", products);
            // products.forEach(e -> logger.debug(e));

            // be sure that when we enter on the main application page (products.jsp), user's info is full and correct
            if (model.asMap().get("user") == null) {
                model.addAttribute("userCart", null);
            } else {
                User currentUser = (User) model.asMap().get("user");
                Cart userCart = null;
                if (model.asMap().get("userCart") == null) {
                    int userId = currentUser.getId();
                    userCart = cartService.getCart(userId);
                    if (userCart == null) {
                        userCart = new Cart(userId);
                    }
                    model.addAttribute("userCart", userCart);
                }
            }
        } else { // connection to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return "products";
    }
}
