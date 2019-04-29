package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Cart;

public interface CartDao {

    Cart getCart(int userId);

    void addProduct(int userId, int productId, int quantity);

    void removeProduct(int userId, int productId, int quantity);

    int deleteCart(int userId);

}
