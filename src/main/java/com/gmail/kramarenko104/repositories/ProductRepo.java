package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Product;

import java.util.List;

public interface ProductRepo {

    long createProduct(Product product);

    List<Product> getProductsByCategory(int category);
}
