package com.gmail.kramarenko104;

import com.gmail.kramarenko104.controller.ProductServlet;
import com.gmail.kramarenko104.dao.ProductDao;
import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RunApp {

    public static void main(String[] args) {

        DaoFactory daoFactory = DaoFactory.getSpecificDao();
//        ProductDao productDao = daoFactory.getProductDao();
////        req.setAttribute("categories", productDao.getCategoriesList());
//        String selectedCateg = "3";
//        List<Product> products;
//
//        // when form is opened at the first time (selectedCateg == null)
//        if (selectedCateg != null) {
//            products = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
//        } else {
//            products = productDao.getAllProducts();
//        }
//
//        products.forEach(e -> System.out.println(e));
//        daoFactory.closeConnection();

        UserDao userDao = daoFactory.getUserDao();
        User user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        user.setName("Alexander");
        user.setAddress("Odessa");
        user.setComment("admin");
        userDao.createUser(user);

        user = new User();
        user.setLogin("lex@gmail.com");
        user.setPassword("A2345678");
        user.setName("Alex");
        user.setAddress("Kiev");
        user.setComment("call before delivery");
        userDao.createUser(user);

        user = new User();
        user.setLogin("mash198@ukr.net");
        user.setPassword("1111111");
        user.setName("Maria");
        user.setAddress("Lviv");
        user.setComment("d'ont call");
        userDao.createUser(user);


//        ProductDao productDao = daoFactory.getProductDao();
////        List<Integer> categoriesList = prodController.getCategoriesList();
////        List<String> categoriesListS = prodController.getCategoriesListS();
//////        categoriesListS.forEach(e -> {
//////            System.out.println("cat from db: " + e);
//////        });
////
////        //String iselectedCategory =  req.getParameter("selectedCat");
////        String iselectedCategory =  "1";
////		int selectedCategory = (iselectedCategory == null? 0: Integer.parseInt(iselectedCategory));
////
//        List<Product> products = null;
////         //filter by category
////		if (selectedCategory != 0){
////            products = prodController.getProductsByCategory(selectedCategory);
////        } else
////        { // show all products
//            products = productDao.getAllProducts();
////        }
//        System.out.println("products = " + products);
//        daoFactory.closeConnection();
    }
}
