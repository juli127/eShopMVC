package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.CartDaoImpl;
import com.gmail.kramarenko104.model.Cart;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDaoImpl cartDao;
    private static Logger logger = LoggerFactory.getLogger(CartService.class);

    public void setCartDao(CartDaoImpl cartDao){
        this.cartDao = cartDao;
    }

    public Cart getCart(long userId){
        Cart userCart = cartDao.getCart(userId);
        if (userCart == null) {
            logger.debug("CartServlet: cart from DB == null! create new cart for userId: " + userId);
            userCart = new Cart(userId);
        }
        return userCart;
    }

    public void addProduct(long userId, long productId, int quantity) {
        cartDao.addProduct(userId, productId, quantity);
    }

    public void removeProduct(long userId, long productId, int quantity){
        cartDao.removeProduct(userId, productId, quantity);
    }

    public void deleteCart(long userId){
        cartDao.deleteCart(userId);
    }

    public Session openSession() {
        return cartDao.getSessionFactory().openSession();
    }

    public void closeSession(){
        cartDao.getSessionFactory().getCurrentSession().close();
    }

    public void addCart(Cart cart) {
        cartDao.addCart(cart);
    }
}
