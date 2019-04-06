package com.gmail.kramarenko104;

import java.io.*;

public class RunApp {

    public static void main(String[] args) throws IOException {


        new Mytest().test();


//        DaoFactory daoFactory = DaoFactory.getSpecificDao();
//        ProductDao productDao = daoFactory.getProductDao();
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
//        daoFactory.deleteProductDao(productDao);

//        UserDao userDao = daoFactory.getUserDao();
//        User user = new User();
//        user.setLogin("admin");
//        user.setPassword("admin");
//        user.setName("Alexander");
//        user.setAddress("Odessa");
//        user.setComment("admin");
//        userDao.createUser(user);
//
//        user = new User();
//        user.setLogin("lex@gmail.com");
//        user.setPassword("A2345678");
//        user.setName("Alex");
//        user.setAddress("Kiev");
//        user.setComment("call before delivery");
//        userDao.createUser(user);
//
//        user = new User();
//        user.setLogin("mash198@ukr.net");
//        user.setPassword("1111111");
//        user.setName("Maria");
//        user.setAddress("Lviv");
//        user.setComment("d'ont call");
//        userDao.createUser(user);


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

class Mytest {
    public void test(){
        ClassLoader classLoader = getClass().getClassLoader();
        String configFile = new File(classLoader.getResource("hikari.properties").getFile()).getAbsolutePath();
        System.out.println(configFile);
    }

}
