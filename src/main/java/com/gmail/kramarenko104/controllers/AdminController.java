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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        logger.debug("[eshop] AdminCntrl.addNewProduct:  POST  enter.....");
        if (em != null) {
            Product newProduct = new Product();
            newProduct.setName(name);
            int categoryInt = ("dress".equals(category) ? 1 : ("shoes".equals(category) ? 2 : 3));
            newProduct.setCategory(categoryInt);
            newProduct.setPrice(Integer.valueOf(price));
            newProduct.setDescription(description);
            newProduct.setImage(image);
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
        return new ModelAndView("adminNewUser");
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ModelAndView addNewUser(@RequestParam("name") String name,
                                   @RequestParam("login") String login,
                                   @RequestParam("password") String password,
                                   @RequestParam("repassword") String repassword,
                                   @RequestParam("address") String address,
                                   @RequestParam("comment") String comment) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        logger.debug("[eshop] AdminCntrl.addNewUser:  POST  enter.....");
        if (em != null) {
            StringBuilder message = new StringBuilder();
            Map<String, String> errors = new HashMap<>();
            StringBuilder errorsMsg = new StringBuilder();

            Map<String, String> regData = new HashMap<>();
            regData.put("login", login);
            regData.put("pass", password);
            regData.put("repass", repassword);
            regData.put("name", name);
            regData.put("address", address);

            for (Map.Entry<String, String> entry : regData.entrySet()) {
                if (entry.getValue() == null || entry.getValue().length() < 1) {
                    errors.put(entry.getKey(), "cannot be empty!");
                }
            }
            if (repassword.length() > 0 && !password.equals(repassword)) {
                errors.put("", "Password and retyped one don't match!");
            }

            if (password.length() < 4) {
                errors.put("", "Password should has minimum 4 symbols!");
            }

            String patternString = "([0-9a-zA-Z._-]+@[0-9a-zA-Z_-]+[.]{1}[a-z]+)";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(login);
            if (!matcher.matches()) {
                errors.put("", "e-mail should have correct format");
            }

            if (errors.size() == 0) {
                // all fields on registration form are filled correctly
                User newUser = new User();
                newUser.setName(name);
                newUser.setLogin(login);
                newUser.setPassword(password);
                newUser.setAddress(address);
                newUser.setComment(comment);
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
