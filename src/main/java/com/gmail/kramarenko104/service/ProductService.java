package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Product;
import java.util.List;

public interface ProductService {

    int addProduct(Product product);

    Product getProduct(int id);

    int deleteProduct(int id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int category);

    boolean sessionIsOpen();
}
