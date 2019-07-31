package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.services.ProductService;
import com.gmail.kramarenko104.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@SessionAttributes(value = {"productsList", "usersList", "warning"})
@RequestMapping("/admin")
public class AdminController {

    private static final String DB_WARNING = "Check your connection to DB!";
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    private UserService userService;
    private ProductService productService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public AdminController(UserService userService,
                           ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllInfo() {
        ModelAndView modelAndView = new ModelAndView("admin");
        if (em != null) {
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
    public ModelAndView prepareToAddNewProduct() {
        return new ModelAndView("adminNewProduct");
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public ModelAndView addNewProduct(@RequestParam("name") String name,
                                      @RequestParam("category") String category,
                                      @RequestParam("price") String price,
                                      @RequestParam("description") String description,
                                      @RequestParam("image") String image) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (em != null) {
            Product newProduct = new Product();
            newProduct.setName(name);
            int categoryInt = ("dress".equals(category) ? 1 : ("shoes".equals(category) ? 2 : 3));
            newProduct.setCategory(categoryInt);
            newProduct.setPrice(Integer.valueOf(price));
            newProduct.setDescription(description);
            newProduct.setImage(image);
            logger.debug("[eshop] adminServlet.addNewProduct: got new product from form: " + newProduct);
            productService.createProduct(newProduct);
            modelAndView.addObject("productsList", productService.getAllProducts());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/products/delete", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@RequestParam long productId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (em != null) {
            productService.deleteProduct(productId);
            modelAndView.addObject("productsList", productService.getAllProducts());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public ModelAndView addNewUser() {
        logger.debug("[eshop] AdminController.addNewUser...GET");
        return new ModelAndView("adminNewUser");
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ModelAndView addNewUser(@RequestParam("name") String name,
                                   @RequestParam("login") String login,
                                   @RequestParam("password") String password,
                                   @RequestParam("address") String address,
                                   @RequestParam("comment") String comment) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (em != null) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setAddress(address);
            newUser.setComment(comment);
            User userDb = userService.createUser(newUser);
            logger.debug("[eshop] AdminController.addNewUser...POST: creted new user: " + userDb);
            modelAndView.addObject("usersList", userService.getAllUsers());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

    @RequestMapping(value = "users/delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam long userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (em != null) {
            userService.deleteUser(userId);
            modelAndView.addObject("usersList", userService.getAllUsers());
        } else { // connection to DB is closed
            modelAndView.addObject("warning", DB_WARNING);
        }
        return modelAndView;
    }

}
