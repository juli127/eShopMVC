package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.CartDaoImpl;
import com.gmail.kramarenko104.model.Cart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartDaoImpl cartDao;
    private static Logger logger = Logger.getLogger(CartService.class);

    public Cart getCart(int userId){
        Cart userCart = cartDao.getCart(userId);
        if (userCart == null) {
            logger.debug("CartServlet: cart from DB == null! create new cart for userId: " + userId);
            userCart = new Cart(userId);
        }
        return userCart;
    }

    public void addProduct(int userId, int productId, int quantity) {
        cartDao.addProduct(userId, productId, quantity);
    }

    public void removeProduct(int userId, int productId, int quantity){
        cartDao.removeProduct(userId, productId, quantity);
    }

    public void deleteCart(int userId){
        cartDao.deleteCart(userId);
    }

    public boolean sessionIsOpen() {
        return cartDao.sessionIsOpen();
    }
}
