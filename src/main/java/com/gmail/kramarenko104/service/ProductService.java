package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Product;
import java.util.List;

public interface ProductService {

    long addProduct(Product product);

    Product getProduct(long id);

    long deleteProduct(long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int category);

}
