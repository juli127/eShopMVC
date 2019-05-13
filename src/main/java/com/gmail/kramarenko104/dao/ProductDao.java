package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import java.util.List;

public interface ProductDao {

    long createProduct(Product product);

    Product getProduct(long id);

    void deleteProduct(long id);

    long updateProduct(Product product);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int category);

}
