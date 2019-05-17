package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;

import java.util.List;

public interface CartDao {

    long createCart(Cart cart);

    Cart addProduct(long userId, Product product, int quantity);

    Cart removeProduct(long userId, Product product, int quantity);

    Cart getCartByUserId(long userId);

    void deleteCartByUserId(long userId);

    List<Cart> getAll();

}