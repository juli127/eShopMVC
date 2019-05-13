package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;

import java.util.List;

public interface CartDao {

    long createCart(Cart cart);

    void addProduct(long userId, Product product, int quantity);

    void removeProduct(long userId, Product product, int quantity);

    Cart getCartByUserId(long userId);

    void deleteCartByUserId(long userId);

    List<Cart> getAllCarts();

}