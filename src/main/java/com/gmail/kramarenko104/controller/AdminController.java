package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.ProductServiceImpl;
import com.gmail.kramarenko104.service.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String DB_WARNING = "Check your connection to DB!";
    private static Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;

    public AdminController() {
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        if (userService.sessionIsOpen()) {
            List<User> usersList = userService.getAllUsers();
            model.addAttribute("usersList", usersList);
        } else { // connection to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return "admin";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getAllProducts(Model model) {
        if (productService.sessionIsOpen()) {
            List<Product> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
        } else { // connection to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return "admin";
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String addNewProduct(@RequestParam("name") String name,
                                @RequestParam("category") String category,
                                @RequestParam("price") String price,
                                @RequestParam("description") String description,
                                @RequestParam("image") String image,
                                Model model) {
        if (productService.sessionIsOpen()) {
            Product newProduct = new Product();
            newProduct.setName(name);
            int categoryInt = ("dress".equals(category) ? 1 : ("shoes".equals(category) ? 2 : 3));
            newProduct.setCategory(categoryInt);
            newProduct.setPrice(Integer.valueOf(price));
            newProduct.setDescription(description);
            newProduct.setImage(image);
            logger.debug("adminServlet.addNewProduct: got from form new product: " + newProduct);
            productService.addProduct(newProduct);
        } else { // connection to DB is closed
            model.addAttribute("warning", DB_WARNING);
        }
        return "admin";
    }
}
