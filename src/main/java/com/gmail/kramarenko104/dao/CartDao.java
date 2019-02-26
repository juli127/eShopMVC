package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import java.util.Map;

public interface CartDao {

    void createCart(int userId);
    Map<Product, Integer> getProductsInCart(int userId);
    void addProduct(int userId, int productId, int quantity);
    void removeProduct(int userId, int productId, int quantity);
    void deleteCart(int userId);

    int getCartSize(int userId);
    int getTotalSum(int userId);

}
