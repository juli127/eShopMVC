package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;

import java.util.List;

public interface ProductDao {

    long createProduct(Product product);

    Product get(long productId);

    Product update(Product product);

    void delete(long productId);

    List<Product> getAll();

    List<Product> getProductsByCategory(int category);

}
