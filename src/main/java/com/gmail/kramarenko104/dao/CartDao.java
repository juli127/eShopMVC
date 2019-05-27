package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;

import java.util.List;

public interface CartDao {

    Cart createCart(long userId);

    void addProduct(long userId, Product product, int quantity);

    void removeProduct(long userId, Product product, int quantity);

    Cart getCartByUserId(long userId);

    void clearCartByUserId(long userId);

    List<Cart> getAll();

}