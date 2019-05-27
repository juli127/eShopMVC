package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Cart;

public interface CartService {

    Cart createCart(long userId);

    Cart getCartByUserId(long userId);

    void addProduct(long cartId, long productId, int quantity);

    void removeProduct(long userId, long productId, int quantity);

    void clearCartByUserId(long Id);

}
