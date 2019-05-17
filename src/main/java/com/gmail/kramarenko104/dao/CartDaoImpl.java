package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.hibernate.EntityManagerFactoryUtil;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Map;

@Repository
@DynamicUpdate
public class CartDaoImpl extends BaseDao<Cart> implements CartDao {

    private final static Logger logger = LoggerFactory.getLogger(CartDaoImpl.class);
    private EntityManagerFactory emf;

    @Autowired
    private UserDaoImpl userDao;

    //@Autowired
    //private SessionFactory sessionFactory;
    //private Session session;

//    @Autowired
//    public CartDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public CartDaoImpl(UserDaoImpl userDao) {
        //sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        emf = EntityManagerFactoryUtil.getEntityManagerFactory();
        this.userDao = userDao;
    }

    @Override
    public long createCart(Cart cart) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long cartId = -1;
        try {
            tx.begin();
            cart.setUser(em.merge(cart.getUser()));
            em.persist(cart);
            tx.commit();
            cartId = cart.getCartId();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return cartId;
    }

    @Override
    public Cart addProduct(long userId, Product product, int addQuantity) {
        logger.debug("++++CartDao.addProduct: for userId: " + userId + ", product: " + product + ", quantity: " + addQuantity);
        int dbQuantity = 0;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Cart resultCart = null;
        try {
            tx.begin();
            TypedQuery<Cart> query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class).setParameter("userId", userId);
            Cart cart = query.getSingleResult();
            if (cart.getProducts().containsKey(product)) {
                dbQuantity = cart.getProducts().get(product);
            }
            cart.getProducts().put(product, dbQuantity + addQuantity);
            Cart updcart = recalculateCart(cart);
            cart.setItemsCount(updcart.getItemsCount());
            cart.setTotalSum(updcart.getTotalSum());
            resultCart = cart;
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return resultCart;
    }

    @Override
    public Cart removeProduct(long userId, Product product, int rmQuantity) {
        logger.debug("----CartDao.deleteProduct: for userId: " + userId + ", product: " + product + ", quantity: " + rmQuantity);
        int dbQuantity = 0;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Cart resultCart = null;
        tx.begin();
        try {
            TypedQuery<Cart> query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class).setParameter("userId", userId);
            Cart cart = query.getSingleResult();
            if (cart.getProducts().containsKey(product)) {
                dbQuantity = cart.getProducts().get(product);
            }
            // there is already such product in dB, just update quantity
            if (dbQuantity > 0) {
                // cannot remove more then we have
                if (dbQuantity < rmQuantity) {
                    rmQuantity = dbQuantity;
                }
                if (rmQuantity != dbQuantity) {
                    logger.debug("----CartDao.deleteProduct: there is such product in dB, update quantity");
                    cart.getProducts().put(product, dbQuantity - rmQuantity);
                    tx.commit();
                } else {
                    // there is only 1 quantity of the product, so, deleteProduct this record from DB
                    logger.debug("----CartDao.deleteProduct: there is only one product in dB, so, deleteProduct it from DB");
                    cart.getProducts().remove(product);
                    tx.commit();
                }
                Cart updcart = recalculateCart(cart);
                cart.setItemsCount(updcart.getItemsCount());
                cart.setTotalSum(updcart.getTotalSum());
                resultCart = cart;
            } else  // there isn't such product in db, noting to do
            { }
            // check if there are any other products in the cart: if cart is empty, delete it
            tx.begin();
            query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class).setParameter("userId", userId);
            cart = query.getSingleResult();
            if (cart.getProducts().size() == 0) {
                em.remove(cart);
                resultCart = null;
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }

         return resultCart;
    }

    @Override
    public Cart getCartByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Cart> query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class).setParameter("userId", userId);
        Cart cart = query.getSingleResult();

        if (cart == null) {
            cart = new Cart();
            User currentUser = userDao.get(userId);
            cart.setUser(currentUser);
            long cartId = createCart(cart);
            cart = em.find(Cart.class, cartId);
        }
        cart = recalculateCart(cart);
        logger.debug("CartDao.getCartByUserId: return cartSum: " + cart.getTotalSum() + ", getItemsCount:" + cart.getItemsCount());
        em.close();
        return cart;
    }

    private Cart recalculateCart(Cart cart){
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

    @Override
    public void deleteCartByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Cart> query = em.createNamedQuery("GET_CART_BY_USERID", Cart.class).setParameter("userId", userId);
            Cart cartToRemove = query.getSingleResult();
            em.remove(cartToRemove);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

}
