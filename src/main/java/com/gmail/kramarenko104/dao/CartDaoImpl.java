package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.factoryDao.HibernateSessionFactoryUtil;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CartDaoImpl implements CartDao {

    private final static String ADD_TO_CART = "INSERT INTO carts (userId, productId, quantity) VALUES(?,?,?);";
    private final static String DELETE_CART = "DELETE FROM carts WHERE userId = ?;";
    private final static String DELETE_PRODUCT = "DELETE FROM carts WHERE userId = ? AND productId = ?;";
    private final static String UPDATE_CART = "UPDATE carts SET quantity = ? WHERE userId =? AND productId = ?;";
    private final static String GET_PRODUCTS_BY_USERID_AND_PRODUCTID = "SELECT * FROM carts WHERE userId =? AND productId = ?;";
    private final static String GET_ALL_PRODUCTS_FROM_CART = "SELECT products.*, carts.quantity FROM products INNER JOIN carts ON products.id = carts.productId WHERE carts.userId = ?;";
    private static Logger logger = Logger.getLogger(CartDaoImpl.class);
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public CartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cart getCart(int userId) {
        Cart cart = null;
        ResultSet rs = null;
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Map<Product, Integer> productsMap = new HashMap<>();
        session = sessionFactory.openSession();
        try (PreparedStatement pst = conn.prepareStatement(GET_ALL_PRODUCTS_FROM_CART)) {
            pst.setInt(1, userId);
            rs = pst.executeQuery();
            int totalSum = 0;
            int itemsCount = 0;
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getInt("category"));
                product.setDescription(rs.getString("description"));
                int price = rs.getInt("price");
                product.setPrice(price);
                product.setImage(rs.getString("image"));
                int quantity = rs.getInt("quantity");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("CartDao.getCart: return cart: " + cart);
        session.close();
        return cart;
    }

    @Override
    public void addProduct(int userId, int productId, int addQuantity) {
        logger.debug("++++CartDao.createProduct: for userId: " + userId + ", productId: " + productId + ", quantity: " + addQuantity);
        int dbQuantity = getProductQuantity(userId, productId);
        session = sessionFactory.openSession();
        // there is already such product in dB, just update quantity
        if (dbQuantity > 0) {
            logger.debug("++++CartDao.createProduct: there is already such product in dB, just update quantity");
            try (PreparedStatement pst = conn.prepareStatement(UPDATE_CART)) {
                conn.setAutoCommit(false);
                pst.setInt(1, dbQuantity + addQuantity);
                pst.setInt(2, userId);
                pst.setInt(3, productId);
                pst.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else { // there isn't such product in dB, add it
            logger.debug("++++CartDao.createProduct: there isn't such product in dB, add it");
            logger.debug("++++CartDao.createProduct: userId: " + userId + ", productId: " + productId + ", addQuantity: " + addQuantity);
            try (PreparedStatement pst = conn.prepareStatement(ADD_TO_CART)) {
                conn.setAutoCommit(false);
                pst.setInt(1, userId);
                pst.setInt(2, productId);
                pst.setInt(3, addQuantity);
                pst.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        session.close();
    }

    private int getProductQuantity(int userId, int productId) {
        ResultSet rs = null;
        int quantity = 0;
        session = sessionFactory.openSession();
        try (PreparedStatement pst = conn.prepareStatement(GET_PRODUCTS_BY_USERID_AND_PRODUCTID)) {
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            rs = pst.executeQuery();
            while (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("CartDao.getProductQuantity: there is " + quantity + " pieces of product " + productId + " for userId " + userId + " in the cart");
        session.close();
        return quantity;
    }

    @Override
    public void removeProduct(int userId, int productId, int rmQuantity) {
        logger.debug("----CartDao.removeProduct: for userId: " + userId + ", productId: " + productId + ", quantity: " + rmQuantity);
        int dbQuantity = getProductQuantity(userId, productId);
        session = sessionFactory.openSession();

        // there is already such product in dB, just update quantity
        if (dbQuantity > 0) {
            if (dbQuantity > 1) {
                logger.debug("----CartDao.removeProduct: there is such product in dB, update quantity");

                try (PreparedStatement pst = conn.prepareStatement(UPDATE_CART)) {
                    session.beginTransaction();

                    // cannot remove more then we have
                    if (dbQuantity < rmQuantity) {
                        rmQuantity = dbQuantity;
                    }
                    pst.setInt(1, dbQuantity - rmQuantity);
                    pst.setInt(2, userId);
                    pst.setInt(3, productId);
                    pst.executeUpdate();
                    session.getTransaction().commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                    session.getTransaction().rollback();
                }
            } else {  // there is only 1 quantity of the product, so, delete this record from DB
                logger.debug("----CartDao.removeProduct: there is only one product in dB, so, delete it from DB");
                try (PreparedStatement pst = conn.prepareStatement(DELETE_PRODUCT)) {
                    pst.setInt(1, userId);
                    pst.setInt(2, productId);
                    pst.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else { //there isn't such product in dB, do nothing
        }
        session.close();
    }

    @Override
    public void deleteCart(int userId) {
        session = sessionFactory.openSession();
        try (PreparedStatement pst = conn.prepareStatement(DELETE_CART)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.close();
    }

    public boolean sessionIsOpen() {
        return sessionFactory.isOpen();
    }
}
