package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Cart;

public interface CartService {

    Cart getCart(int userId);

    void addProduct(int userId, int productId, int quantity);

    void removeProduct(int userId, int productId, int quantity);

    void deleteCart(int userId);

    boolean sessionIsOpen();
}
