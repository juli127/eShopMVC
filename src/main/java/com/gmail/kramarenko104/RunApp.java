package com.gmail.kramarenko104;

import com.gmail.kramarenko104.controllers.ProductController;
import com.gmail.kramarenko104.models.Product;

import java.util.List;

public class RunApp {

    public static void main(String[] args) {

        ProductController prodController = new ProductController();
        List<Integer> categoriesList = prodController.getCategoriesList();
        categoriesList.forEach(e -> {
            System.out.println("cat from db: " + e);
        });

        //String iselectedCategory =  req.getParameter("selectedCat");
        String iselectedCategory =  "1";
		int selectedCategory = (iselectedCategory == null? 0: Integer.parseInt(iselectedCategory));

        List<Product> products = null;
         //filter by category
		if (selectedCategory != 0){
            products = prodController.getProductsByCategory(selectedCategory);
        } else
        { // show all products
            products = prodController.getAllProducts();
        }
        products.forEach(e -> System.out.println(e));
        prodController.close();
    }
}
