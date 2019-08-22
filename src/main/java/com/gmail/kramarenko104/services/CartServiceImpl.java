package com.gmail.kramarenko104.services;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.repositories.CartRepoImpl;
import com.gmail.kramarenko104.repositories.ProductRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private CartRepoImpl cartRepo;
    private ProductRepoImpl productRepo;

    @Autowired
    public CartServiceImpl(CartRepoImpl cartRepo, ProductRepoImpl productRepo){
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Cart createCart(long userId) {
        return cartRepo.createCart(userId);
    }

    @Override
    public Cart getCartByUserId(long userId) {
        Cart cart = cartRepo.getCartByUserId(userId);
        if (cart != null && cart.getProducts().size() > 0) {
            cart = recalculateCart(cart);
        }
        return cart;
    }

    @Override
    public void addProduct(long userId, long productId, int quantity) {
        cartRepo.addProduct(userId, productRepo.get(productId), quantity);
    }

    @Override
    public void removeProduct(long userId, long productId, int quantity) {
        cartRepo.removeProduct(userId, productRepo.get(productId), quantity);
    }

    @Override
    public void clearCartByUserId(long userId) {
        cartRepo.clearCartByUserId(userId);
    }

    private Cart recalculateCart(Cart cart) {
        Map<Product, Integer> productsInCart = cart.getProducts();
        int itemsCount = 0;
        int totalSum = 0;
        if (productsInCart.size() > 0) {
            int quantity = 0;
            for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
                quantity = entry.getValue();
                itemsCount += quantity;
                totalSum += quantity * entry.getKey().getPrice();
            }
        }
        if (itemsCount > 0) {
            cart.setItemsCount(itemsCount);
            cart.setTotalSum(totalSum);
        }
        return cart;
    }
}
