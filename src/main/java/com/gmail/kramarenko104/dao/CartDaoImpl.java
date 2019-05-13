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
import java.util.List;
import java.util.Map;

@Repository
@DynamicUpdate
public class CartDaoImpl implements CartDao {

    private final static String GET_ALL_CARTS = "from Cart c";
    private final static String GET_CART_BY_USERID = "from Cart c where c.user.userId = :userId";
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
    public void addProduct(long userId, Product product, int addQuantity) {
        logger.debug("++++CartDao.addProduct: for userId: " + userId + ", product: " + product + ", quantity: " + addQuantity);
        int dbQuantity = 0;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cart cart = (Cart) em.createQuery(GET_CART_BY_USERID)
                    .setParameter("userId", userId)
                    .getSingleResult();
            if (cart.getProducts().containsKey(product)) {
                dbQuantity = cart.getProducts().get(product);
            }
            cart.getProducts().put(product, dbQuantity + addQuantity);
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
        logger.debug("----CartDao.removeProduct: for userId: " + userId + ", product: " + product + ", quantity: " + rmQuantity);
        int dbQuantity = 0;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Cart cart = (Cart) em.createQuery(GET_CART_BY_USERID).setParameter("userId", userId).getSingleResult();
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
                    logger.debug("----CartDao.removeProduct: there is such product in dB, update quantity");
                    cart.getProducts().put(product, dbQuantity - rmQuantity);
                    tx.commit();
                } else {
                    // there is only 1 quantity of the product, so, deleteProduct this record from DB
                    logger.debug("----CartDao.removeProduct: there is only one product in dB, so, deleteProduct it from DB");
                    cart.getProducts().remove(product);
                    tx.commit();
                }
            } else  // there isn't such product in db, noting to do
            {
            }
            // check if there are any other products in the cart: if cart is empty, deleteProduct it
            tx.begin();
            cart = (Cart) em.createQuery(GET_CART_BY_USERID)
                    .setParameter("userId", userId).getSingleResult();
            if (cart.getProducts().size() == 0) {
                em.remove(cart);
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cart> getAllCarts() {
        EntityManager em = emf.createEntityManager();
        List<Cart> cartsList = em.createQuery(GET_ALL_CARTS).getResultList();
        em.close();
        return cartsList;
    }

    @Override
    public Cart getCartByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        Cart cart = (Cart) em.createQuery(GET_CART_BY_USERID)
                .setParameter("userId", userId).getSingleResult();

        if (cart == null) {
            cart = new Cart();
            User currentUser = userDao.getUser(userId);
            cart.setUser(currentUser);
            long cartId = createCart(cart);
            cart = em.find(Cart.class, cartId);
        }
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
        logger.debug("CartDao.getCartByUserId: return cartSum: " + cart.getTotalSum() + ", getItemsCount:" + cart.getItemsCount());
        em.close();
        return cart;
    }

    @Override
    public void deleteCartByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cart cartToRemove = (Cart) em.createQuery(GET_CART_BY_USERID)
                    .setParameter("userId", userId).getSingleResult();
            System.out.println("!!!! before commit ...em.contains(cartToRemove) === " + em.contains(cartToRemove));
            em.remove(cartToRemove);
            tx.commit();
            System.out.println("!!!! after commit ...em.contains(cartToRemove) === " + em.contains(cartToRemove));
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

}
