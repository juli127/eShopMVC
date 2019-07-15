package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;

public interface CartRepo {

    Cart createCart(long userId);

    void addProduct(long userId, Product product, int quantity);

    void removeProduct(long userId, Product product, int quantity);

    Cart getCartByUserId(long userId);

    void clearCartByUserId(long userId);

}