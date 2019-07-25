package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Repository
public class CartRepoImpl extends BaseRepoImpl<Cart> implements CartRepo {

    private final static Logger logger = LoggerFactory.getLogger(CartRepoImpl.class);

    @PersistenceContext
    private EntityManager em;

    private UserRepoImpl userRepo;

    @Autowired
    public CartRepoImpl(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Cart createCart(long userId) {
        Cart cart = new Cart();
        // make currentUser managed
        User currentUser = em.merge(userRepo.get(userId));
        cart.setUser(currentUser);
        em.persist(cart);
        logger.debug("[eshop] CartRepoImpl.createCart: NEW cart was created: " + cart);
        return cart;
    }

    @Override
    public Cart getCartByUserId(long userId) {
        Cart cart = null;
        try {
            TypedQuery<Cart> query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class)
                    .setParameter("userId", userId);
            cart = query.getSingleResult();
        } catch (NoResultException ex) {
        }
        logger.debug("[eshop] CartRepoImpl.getCartByUserId: return cart: " + cart);
        return cart;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public void clearCartByUserId(long userId) {
        Cart cartToClear = getCartByUserId(userId);
        logger.debug("[eshop] CartRepoImpl.clearCartByUserId: cartToRemove: " + cartToClear);
        // let's cartId stays the same for this userId
        // just remove all products from this cart
        // for this purpose cartToClear should stay in detached status
        // so, 'cart' table won't be clear, but 'carts_products' table will be cleaned - it's expected result
        em.remove(cartToClear);
        logger.debug("[eshop] CartRepoImpl.clearCartByUSerId: cart for userId: " + userId + " was deleted");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void addProduct(long userId, Product product, int addQuantity) {
        logger.debug("[eshop] CartRepoImpl.addProduct: for userId: " + userId + ", product: " + product + ", quantity: " + addQuantity);
        int dbQuantity = 0;
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            if (cart.getProducts().containsKey(product)) {
                dbQuantity = cart.getProducts().get(product);
            }
        } else {
            cart = createCart(userId);
        }
        cart.getProducts().put(product, dbQuantity + addQuantity);
        em.merge(cart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void removeProduct(long userId, Product product, int rmQuantity) {
        logger.debug("[eshop] CartRepoImpl.deleteProduct: for userId: " + userId + ", product: " + product + ", quantity: " + rmQuantity);
        int dbQuantity = 0;
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            if (cart.getProducts().containsKey(product)) {
                dbQuantity = cart.getProducts().get(product);
            }
        } else {
            cart = createCart(userId);
        }
        // there is already such product in dB, just update quantity
        if (dbQuantity > 0) {
            // cannot remove more then we have
            if (dbQuantity < rmQuantity) {
                rmQuantity = dbQuantity;
            }
            if (rmQuantity != dbQuantity) {
                logger.debug("[eshop] CartRepoImpl.deleteProduct: there is such product in dB, update quantity");
                cart.getProducts().put(product, dbQuantity - rmQuantity);
            } else {
                // there is only 1 quantity of the product, so, deleteProduct this record from DB
                logger.debug("[eshop] CartRepoImpl.deleteProduct: there is only one product in dB, so, deleteProduct it from DB");
                cart.getProducts().remove(product);
            }
            em.merge(cart);
        }
    }
}
