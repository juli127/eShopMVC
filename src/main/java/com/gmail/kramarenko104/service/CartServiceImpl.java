package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.CartDaoImpl;
import com.gmail.kramarenko104.dao.ProductDaoImpl;
import com.gmail.kramarenko104.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    //@Autowired
    private CartDaoImpl cartDao;
    //@Autowired
    private ProductDaoImpl productDao;
    private static Logger logger = LoggerFactory.getLogger(CartService.class);

    public void setDaos(CartDaoImpl cartDao, ProductDaoImpl productDao){
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Override
    public long createCart(Cart cart) {
        return cartDao.createCart(cart);
    }

    public Cart getCartByUserId(long userId){
        return cartDao.getCartByUserId(userId);
    }

    @Override
    public void addProduct(long userId, long productId, int quantity) {
        cartDao.addProduct(userId, productDao.getProduct(productId), quantity);
    }

    @Override
    public void removeProduct(long userId, long productId, int quantity) {
        cartDao.removeProduct(userId, productDao.getProduct(productId), quantity);
    }

    @Override
    public void deleteCartByUserId(long userId) {
        cartDao.deleteCartByUserId(userId);
    }

    @Override
    public List<Cart> getAllCartsByUserId(long userId) {
        return null;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartDao.getAllCarts();
    }

}
