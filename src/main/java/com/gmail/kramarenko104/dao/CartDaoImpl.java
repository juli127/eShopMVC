package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.factoryDao.HibernateSessionFactoryUtil;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@DynamicUpdate
public class CartDaoImpl implements CartDao {

    private final static String ENTITY_NAME = "Cart";
    //    private final static String GET_PRODUCT_QUANTITY_FOR_USERID = "select c.quantity from " + ENTITY_NAME + " c where c.userId = :userId and c.productId = :productId";
    private final static String GET_PRODUCT_QUANTITY_FOR_USERID = "select cp.quantity from cart_products cp INNER JOIN carts_test c ON c.cartId = cp.cartId where c.userId = :userId and cp.productId = :productId";
    private final static String ADD_PRODUCT_TO_CART = "insert into " + ENTITY_NAME + " (userId, productId, quantity) VALUES(?,?,?)";
    private final static String DELETE_PRODUCT = "delete from " + ENTITY_NAME + " c WHERE c.userId = :userId and c.productId = :productId";
    private final static String UPDATE_CART = "update " + ENTITY_NAME + " c SET c.quantity = :quantity where c.userId = :userId and c.productId = :productId";
    private final static String GET_ALL_PRODUCTS_FROM_CART = "SELECT products.*, " + ENTITY_NAME + ".quantity FROM products INNER JOIN " +
            ENTITY_NAME + " ON products.id = " + ENTITY_NAME + ".productId WHERE " + ENTITY_NAME + ".userId = ?";
    //    private final static String DELETE_CART = "delete from " + ENTITY_NAME + " c where c.userId = :userId";
    private final static Logger logger = LoggerFactory.getLogger(CartDaoImpl.class);

    ///    @Autowired
    private SessionFactory sessionFactory;

//    @Autowired
//    public CartDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public CartDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        System.out.println(">>>. CartDaoImpl.getSessionFactory() = " + sessionFactory);
    }

    @Override
    public Cart getCart(long userId) {
        Cart cart = null;
        Session session = sessionFactory.openSession();
        Map<Product, Integer> productsMap = new HashMap<>();
        session = sessionFactory.openSession();
        List<Object[]> resultList = session.createNativeQuery(GET_ALL_PRODUCTS_FROM_CART)
                .setParameter("userId", userId)
                .getResultList();
        session.close();

        int totalSum = 0;
        int itemsCount = 0;

        for (Object[] resObject : resultList) {
            Product product = new Product();
            product.setProductId((Long) resObject[0]);
            product.setName((String) resObject[1]);
            product.setCategory((Integer) resObject[2]);
            product.setDescription((String) resObject[3]);
            int price = (Integer) resObject[4];
            product.setPrice(price);
            product.setImage((String) resObject[5]);
            int quantity = (Integer) resObject[6];
            productsMap.put(product, quantity);
            itemsCount += quantity;
            totalSum += quantity * price;
        }

        if (itemsCount > 0) {
            cart = new Cart(userId);
            cart.setProducts(productsMap);
            cart.setItemsCount(itemsCount);
            cart.setTotalSum(totalSum);
        }
        logger.debug("CartDao.getCart: return cart: " + cart);
        return cart;
    }

    @Override
    public void addProduct(long userId, long productId, int addQuantity) {
        logger.debug("++++CartDao.createProduct: for userId: " + userId + ", productId: " + productId + ", quantity: " + addQuantity);
        int dbQuantity = getProductQuantity(userId, productId);
//        int dbQuantity = 0;
        Session session = sessionFactory.openSession();
        // there is already such product in dB, just update quantity
        Transaction tx = session.beginTransaction();
        if (dbQuantity > 0) {
            logger.debug("++++CartDao.createProduct: there is already such product in dB, just update quantity");
            session.createQuery(UPDATE_CART)
                    .setParameter("quantity", dbQuantity + addQuantity)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .executeUpdate();
            tx.commit();

        } else { // there isn't such product in dB, add it
            logger.debug("++++CartDao.createProduct: there isn't such product in dB, add it");
            logger.debug("++++CartDao.createProduct: userId: " + userId + ", productId: " + productId + ", addQuantity: " + addQuantity);
            session.createNativeQuery(ADD_PRODUCT_TO_CART)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .setParameter("quantity", addQuantity)
                    .executeUpdate();
            tx.commit();
        }
        session.close();
    }

    private int getProductQuantity(long userId, long productId) {
        Session session = sessionFactory.openSession();

        int quantity = (int) session.createNativeQuery(GET_PRODUCT_QUANTITY_FOR_USERID)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getSingleResult();
        logger.debug("CartDao.getProductQuantity: there is " + quantity + " pieces of product " + productId + " for userId " + userId + " in the cart");
        session.close();
        return quantity;
    }

    @Override
    public void removeProduct(long userId, long productId, int rmQuantity) {
        logger.debug("----CartDao.removeProduct: for userId: " + userId + ", productId: " + productId + ", quantity: " + rmQuantity);
        int dbQuantity = getProductQuantity(userId, productId);
        Session session = sessionFactory.openSession();

        // there is already such product in dB, just update quantity
        if (dbQuantity > 0) {
            Transaction tx = session.beginTransaction();
            if (dbQuantity > 1) {
                logger.debug("----CartDao.removeProduct: there is such product in dB, update quantity");
                // cannot remove more then we have
                if (dbQuantity < rmQuantity) {
                    rmQuantity = dbQuantity;
                }
                session.createQuery(UPDATE_CART)
                        .setParameter("quantity", dbQuantity - rmQuantity)
                        .setParameter("userId", userId)
                        .setParameter("productId", productId)
                        .executeUpdate();
                tx.commit();
            } else {
                // there is only 1 quantity of the product, so, delete this record from DB
                logger.debug("----CartDao.removeProduct: there is only one product in dB, so, delete it from DB");
                session.getTransaction().begin();
                session.createQuery(DELETE_PRODUCT)
                        .setParameter("userId", userId)
                        .setParameter("productId", productId)
                        .executeUpdate();
                tx.commit();
            }
        } else { //there isn't such product in dB, do nothing
        }
        session.close();
    }

    @Override
    public long deleteCart(long userId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        long identifier = -1;
        Cart cart = session.get(Cart.class, userId);
        if (cart != null) {
            session.delete(ENTITY_NAME, cart);
            session.flush();
            identifier = (long) session.getIdentifier(cart);
        }
        tx.commit();
        session.close();
        return identifier;
    }

    public void addCart(Cart cart) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(ENTITY_NAME, cart);
        session.flush();
        tx.commit();
        session.close();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
