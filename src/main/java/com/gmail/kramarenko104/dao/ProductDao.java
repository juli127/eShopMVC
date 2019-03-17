package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import java.util.List;

public interface ProductDao {

    // CRUD functionality
    boolean addProduct(Product product);
    Product getProduct(int id);
    boolean deleteProduct(int id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(int category);

}
