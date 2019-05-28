package com.gmail.kramarenko104.controller;

import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.service.ProductServiceImpl;
import com.gmail.kramarenko104.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Controller
@SessionAttributes(value = {"productsList", "usersList", "warning"})
@RequestMapping("/admin")
public class AdminController {

    private static final String DB_WARNING = "Check your connection to DB!";
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private EntityManagerFactory emf;

    public AdminController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllInfo() {
        ModelAndView modelAndView = new ModelAndView("admin");
        if (emf != null) {
            List<User> usersList = userService.getAllUsers();
            List<Product> productsList = productService.getAllProducts();
            modelAndView.addObject("productsList", productsList);
            modelAndView.addObject("usersList", usersList);
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public ModelAndView addNewProduct() {
        return new ModelAndView("adminNewProduct");
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public ModelAndView addNewProduct(@RequestParam("name") String name,
                                      @RequestParam("category") String category,
                                      @RequestParam("price") String price,
                                      @RequestParam("description") String description,
                                      @RequestParam("image") String image) {
        ModelAndView modelAndView = new ModelAndView("admin");
        if (emf != null) {
            Product newProduct = new Product();
            newProduct.setName(name);
            int categoryInt = ("dress".equals(category) ? 1 : ("shoes".equals(category) ? 2 : 3));
            newProduct.setCategory(categoryInt);
            newProduct.setPrice(Integer.valueOf(price));
            newProduct.setDescription(description);
            newProduct.setImage(image);
            logger.debug("adminServlet.addNewProduct: got new product from form: " + newProduct);
            productService.createProduct(newProduct);
            modelAndView.addObject("productsList", productService.getAllProducts());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/products/delete", method = RequestMethod.POST)
    public ModelAndView deleteProduct(@RequestParam int productId) {
        ModelAndView modelAndView = new ModelAndView("admin");
        if (emf != null) {
            productService.deleteProduct(productId);
            modelAndView.addObject("productsList", productService.getAllProducts());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public ModelAndView addNewUser() {
        System.out.println("AdminController.addNewUser.GET");
        return new ModelAndView("adminNewUser");
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ModelAndView addNewUser(@RequestParam("login") String login,
                                   @RequestParam("password") String password,
                                   @RequestParam("name") String name,
                                   @RequestParam("address") String address,
                                   @RequestParam("comment") String comment) {
        ModelAndView modelAndView = new ModelAndView("admin");
        System.out.println("AdminController.addNewUser.POST");
        if (emf != null) {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setName(name);
            newUser.setAddress(address);
            newUser.setComment(comment);
            newUser = userService.createUser(newUser);
            System.out.println("adminServlet.addNewUser: got new user from form: " + newUser);
            modelAndView.addObject("usersList", userService.getAllUsers());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam int userId) {
        ModelAndView modelAndView = new ModelAndView("admin");
        if (emf != null) {
            userService.deleteUser(userId);
            modelAndView.addObject("usersList", userService.getAllUsers());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }
}
