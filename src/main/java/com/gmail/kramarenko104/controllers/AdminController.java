package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.services.ProductService;
import com.gmail.kramarenko104.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@SessionAttributes(value = {"productsList", "usersList", "warning", "userForm"})
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
        ModelAndView modelAndView = new ModelAndView("adminNewProduct");
        Map categoryMap = new LinkedHashMap();
        categoryMap.put(1, "dress");
        categoryMap.put(2, "shoes");
        categoryMap.put(3, "accessories");
        modelAndView.addObject("categoryMap", categoryMap);
        modelAndView.addObject("productForm", new Product());
        return modelAndView;
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public ModelAndView addNewProduct(@ModelAttribute("productForm") Product product){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (em != null) {
            Product newProduct = new Product();
            newProduct.setName(product.getName());
            newProduct.setCategory(product.getCategory());
            newProduct.setPrice(product.getPrice());
            newProduct.setDescription(product.getDescription());
            newProduct.setImage(product.getImage());
            logger.debug("[eshop] AdminCntrl.addNewProduct: got new product from form: " + newProduct);
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
        return new ModelAndView("adminNewUser", "userForm", new User());
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ModelAndView addNewUser(@Valid @ModelAttribute("userForm") User user,
                                   BindingResult bindingResult,
                                   @RequestParam String repassword) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        logger.debug("[eshop] AdminCntrl.addNewUser:  POST  enter.....");
        if (em != null) {
            StringBuilder message = new StringBuilder();
            Map<String, String> errors = new HashMap<>();
            StringBuilder errorsMsg = new StringBuilder();
            String login = user.getLogin();
            String password = user.getPassword();

            if (repassword.length() > 0 && !password.equals(repassword)) {
                errors.put("", "Password and retyped one don't match!");
            }

            String patternString = "([0-9a-zA-Z._-]+@[0-9a-zA-Z_-]+[.]{1}[a-z]+)";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(login);
            if (!matcher.matches()) {
                errors.put("", "e-mail should have correct format");
            }

            if (errors.size() == 0 && !bindingResult.hasErrors()) {
                // all fields on registration form are filled correctly
                User newUser = new User();
                newUser.setName(user.getName());
                newUser.setLogin(login);
                newUser.setPassword(password);
                newUser.setAddress(user.getAddress());
                newUser.setComment(user.getComment());
                newUser = userService.createUser(newUser);
                if (newUser != null) {
                    logger.debug("[eshop] AdminCntrl: new user was created: " + newUser);
                    Cart newCart = new Cart();
                    newCart.setUser(newUser);
                } else {
                    logger.debug("[eshop] AdminCntrl: new user was NOT created " );
                }
                // update users' list taking in account the new added user
                modelAndView.addObject("usersList", userService.getAllUsers());
            }
            // some fields on form are filled in wrong way
            else {
                // prepare errorsMsg to show on jsp
                for (Map.Entry<String, String> entry : errors.entrySet()) {
                    errorsMsg.append(entry.getKey()).append(" ").append(entry.getValue()).append("<br>");
                }
                modelAndView.addObject("errorsMsg", errorsMsg);
                modelAndView.setViewName("adminNewUser");
            }
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
