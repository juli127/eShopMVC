package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import java.util.List;

public interface ProductDao {

    int createProduct(Product product);

    Product getProduct(int id);

    int deleteProduct(int id);

    int updateProduct(Product product);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int category);

}
