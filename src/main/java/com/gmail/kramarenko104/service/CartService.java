package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Cart;

public interface CartService {

    Cart getCart(long userId);

    void addProduct(long userId, long productId, int quantity);

    void removeProduct(long userId, long productId, int quantity);

    void deleteCart(long userId);

}
