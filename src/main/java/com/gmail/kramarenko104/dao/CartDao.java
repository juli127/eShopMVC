package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Cart;

public interface CartDao {

    Cart getCart(long userId);

    void addProduct(long userId, long productId, int quantity);

    void removeProduct(long userId, long productId, int quantity);

    long deleteCart(long userId);

}
