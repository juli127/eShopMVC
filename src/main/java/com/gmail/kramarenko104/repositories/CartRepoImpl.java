package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;

@Repository
public class CartRepoImpl extends BaseRepoImpl<Cart> implements CartRepo {

    private final static Logger logger = LoggerFactory.getLogger(CartRepoImpl.class);
    private EntityManagerFactory emf;
    private UserRepoImpl userDao;

    @Autowired
    public CartRepoImpl(EntityManagerFactory emf, UserRepoImpl userDao) {
        this.emf = emf;
        this.userDao = userDao;
    }

    @Override
    public Cart createCart(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Cart cart = new Cart();
        try {
            tx.begin();
            // make currentUser managed
            User currentUser = em.merge(userDao.get(userId));
            cart.setUser(currentUser);
            em.persist(cart);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        logger.debug("[eshop] CartDaoImplImpl.createCart: NEW cart was created: " + cart);
        return cart;
    }

    @Override
    public Cart getCartByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        Cart cart = null;
        try {
            TypedQuery<Cart> query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class)
                    .setParameter("userId", userId);
            cart = query.getSingleResult();
        } catch (NoResultException ex) {
        } finally {
            em.close();
        }
        logger.debug("[eshop] CartRepo.getCartByUserId: return cart: " + cart);
        return cart;
    }

    @Override
    public void clearCartByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Cart cartToClear = getCartByUserId(userId);
        logger.debug("[eshop] CartDaoImplImpl.clearCartByUserId: cartToRemove: " + cartToClear);
        try {
            // let's cartId stays the same for this userId
            // just remove all products from this cart
            // for this purpose cartToClear should stay in detached status
            // so, 'cart' table won't be clear, but 'carts_products' table will be cleaned - it's expected result
            tx.begin();
            em.remove(cartToClear);
            tx.commit();
        } catch (Exception ex) {
        } finally {
            em.close();
        }
        logger.debug("[eshop] CartDaoImplImpl: cart for userId: " + userId + " was deleted");
    }

    @Override
    public void addProduct(long userId, Product product, int addQuantity) {
        logger.debug("[eshop] Julia ++++CartRepo.addProduct: for userId: " + userId + ", product: " + product + ", quantity: " + addQuantity);
        int dbQuantity = 0;
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            if (cart.getProducts().containsKey(product)) {
                dbQuantity = cart.getProducts().get(product);
            }
        } else {
            cart = createCart(userId);
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            cart.getProducts().put(product, dbQuantity + addQuantity);
            em.merge(cart);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void removeProduct(long userId, Product product, int rmQuantity) {
        logger.debug("[eshop] CartRepo.deleteProduct: for userId: " + userId + ", product: " + product + ", quantity: " + rmQuantity);
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

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                if (rmQuantity != dbQuantity) {
                    logger.debug("[eshop] CartRepo.deleteProduct: there is such product in dB, update quantity");
                    cart.getProducts().put(product, dbQuantity - rmQuantity);
                } else {
                    // there is only 1 quantity of the product, so, deleteProduct this record from DB
                    logger.debug("[eshop] CartRepo.deleteProduct: there is only one product in dB, so, deleteProduct it from DB");
                    cart.getProducts().remove(product);
                }
                em.merge(cart);
                tx.commit();
            } catch (Exception ex) {
                tx.rollback();
                ex.printStackTrace();
            } finally {
                em.close();
            }
        }
    }
}
