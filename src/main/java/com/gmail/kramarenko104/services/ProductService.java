package com.gmail.kramarenko104.services;

import com.gmail.kramarenko104.model.Product;
import java.util.List;

public interface ProductService {

    long createProduct(Product product);

    Product getProduct(long productId);

    void deleteProduct(long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int category);

}
