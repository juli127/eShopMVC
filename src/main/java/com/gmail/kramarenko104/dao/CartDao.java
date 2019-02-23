package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.models.Cart;
import com.gmail.kramarenko104.models.Product;
import java.util.Map;

public interface CartDao {

    Cart createCart(int userId);
    Cart getCart(int userId);
    void addProduct(int userId, int productId, int quantity);
    void removeProduct(int userId, int productId, int quantity);
    void deleteCart(int userId);

//    int getSize();
    Map<Product, Integer> getProductsMap();

}
