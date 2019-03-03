package com.gmail.kramarenko104.controller;

import java.util.List;
import javax.servlet.http.HttpServlet;

import com.gmail.kramarenko104.dao.ProductDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import org.apache.log4j.Logger;
import com.gmail.kramarenko104.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/", "/products"})
public class ProductServlet extends HttpServlet{

    private static Logger logger = Logger.getLogger(ProductServlet.class);
    private DaoFactory daoFactory;

    public ProductServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {

        ProductDao productDao = daoFactory.getProductDao();
        String selectedCateg = (String)model.get("selectedCategory");

        List<Product> products;
        // when form is opened at the first time, selectedCateg == null
        if (selectedCateg != null) {
            products = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
        } else {
            products = productDao.getAllProducts();
        }

        model.addAttribute("selectedCateg", selectedCateg);
        model.addAttribute("products", products);
//        products.forEach(e -> System.out.println(e));
        daoFactory.deleteProductDao(productDao);
        return("products");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(ModelMap model) {
        return doGet(model);
    }

    // where to close connection???
    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }

}
