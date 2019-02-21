package com.gmail.kramarenko104;

import com.gmail.kramarenko104.controllers.DBWorker;
import com.gmail.kramarenko104.controllers.UserController;
import java.sql.SQLException;

public class RunApp {

    public static void main(String[] args) {

        DBWorker dbworker = new DBWorker();
//        ProductController prodController = new ProductController();
        UserController userContr = new UserController(dbworker.getConnection());
        try {
            userContr.addUser("alex@ukr.net", "admin", "Alexander", "Odessa", "admin");
            userContr.addUser("lex@gmail.com", "A2345678", "Alex", "Kiev", "call before delivery");
            userContr.addUser("mash198@ukr.net", "1111111", "Maria", "Odessa", "dont call");
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        List<Integer> categoriesList = prodController.getCategoriesList();
//        List<String> categoriesListS = prodController.getCategoriesListS();
////        categoriesListS.forEach(e -> {
////            System.out.println("cat from db: " + e);
////        });
//
//        //String iselectedCategory =  req.getParameter("selectedCat");
//        String iselectedCategory =  "1";
//		int selectedCategory = (iselectedCategory == null? 0: Integer.parseInt(iselectedCategory));
//
//        List<Product> products = null;
//         //filter by category
//		if (selectedCategory != 0){
//            products = prodController.getProductsByCategory(selectedCategory);
//        } else
//        { // show all products
//            products = prodController.getAllProducts();
//        }
//        System.out.println("products.size = " + products.size());
//        prodController.close();
    }
}
