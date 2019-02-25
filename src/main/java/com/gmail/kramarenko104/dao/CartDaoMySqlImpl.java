package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.factoryDao.MySqlDaoFactory;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class CartDaoMySqlImpl implements CartDao {

    private final static String GET_CART_BY_USERID = "SELECT * FROM carts WHERE userId = ?;";
    private final static String ADD_TO_CART = "INSERT INTO carts (userId, productId, quantity) VALUES(?,?,?);";
    private final static String DELETE_CART = "DELETE FROM carts WHERE id = ?;";
    private final static String UPDATE_CART = "UPDATE carts SET quantity = ? WHERE userId =? AND productId = ?;";
    private final static String GET_PRODUCTS = "SELECT * FROM carts WHERE userId =? AND productId = ?;";
    private final static String GET_ALL_FROM_CART = "SELECT products.*, carts.quantity FROM products INNER JOIN carts ON products.id = carts.productId WHERE carts.userId = ?;";
    private static Logger logger = Logger.getLogger(CartDaoMySqlImpl.class);
    private Connection conn;
    private int cartSize;
    private int totalSum = 0;

    public CartDaoMySqlImpl(Connection conn) {
        this.conn = conn;
        cartSize = 0;
    }

    @Override
    public void createCart(int userId) {
        Cart cart = new Cart();
        try (PreparedStatement pst = conn.prepareStatement(ADD_TO_CART)) {
            conn.setAutoCommit(false);
            pst.setInt(1, userId);
            pst.setInt(2, 0);
            pst.setInt(2, 0);
            pst.execute();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(int userId, int productId, int quantity) {
        logger.debug("CartDao.addProduct: enter with userId: " + userId + ", productId: " + productId + ", quantity: " + quantity);
        // there is already such product in dB, just update quantity
        if (productExists(userId, productId)) {
            logger.debug("CartDao.addProduct: there is already such product in dB, just update quantity");
            try (PreparedStatement pst = conn.prepareStatement(UPDATE_CART)) {
                conn.setAutoCommit(false);
                pst.setInt(1, quantity + getProductQuantity(userId, productId));
                pst.setInt(2, userId);
                pst.setInt(3, productId);
                pst.execute();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else { //there isn't such product in dB, add it
            logger.debug("CartDao.addProduct: there isn't such product in dB, add it");
            try (PreparedStatement pst = conn.prepareStatement(ADD_TO_CART)) {
                conn.setAutoCommit(false);
                pst.setInt(1, userId);
                pst.setInt(2, productId);
                pst.setInt(3, quantity);
                pst.execute();
                conn.commit();
                conn.setAutoCommit(true);
                cartSize++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            logger.debug("CartDao.addProduct: updated cart: " + getProductsInCart(userId));
            cartSize += quantity;
            logger.debug("CartDao.addProduct: updated cartSize: " + cartSize);
        }
    }

    private boolean productExists(int userId, int productId) {
//    private boolean productExists(int productId) {
//        return products.keySet().contains(productId);
        ResultSet rs = null;
        boolean exist = false;
        try (PreparedStatement pst = conn.prepareStatement(GET_PRODUCTS)) {
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            rs = pst.executeQuery();
            // check if ResultSet is empty
            if (rs.isBeforeFirst()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        return exist;
    }

    private int getProductQuantity(int userId, int productId) {
        ResultSet rs = null;
        int quantity = 0;
        try (PreparedStatement pst = conn.prepareStatement(GET_PRODUCTS)) {
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            rs = pst.executeQuery();
            while (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        return quantity;
    }

    @Override
    public void removeProduct(int userId, int productId, int quantity) {
        logger.debug("CartDao.removeProduct: enter with userId: " + userId + ", productId: " + productId + ", quantity: " + quantity);
//        if (getProductsSet().contains(product)){
//            qnt = products.get(product) - 1;
//        }
//        products.put(product, qnt);
//        size--;
        cartSize -= quantity;
    }

    @Override
    public void deleteCart(int userId) {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_CART)) {
            pst.setInt(1, userId);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cartSize = 0;
    }

    @Override
    public int getSize() {
        return cartSize;
    }

    @Override
    public int getTotalSum() {
        return totalSum;
    }

    @Override
    public Map<Product, Integer> getProductsInCart(int userId) {
        Map<Product, Integer> productsMap = new HashMap<>();
        int size = 0;
        int quantity = 0;
        logger.debug("CartDao.getProductsInCart: " + GET_ALL_FROM_CART + userId);
        try (PreparedStatement pst = conn.prepareStatement(GET_ALL_FROM_CART)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getInt("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setImage(rs.getString("image"));
                quantity = rs.getInt("quantity");
                productsMap.put(product, rs.getInt("quantity"));
                logger.debug("CartDao.getProductsInCart: GOT " + product + ", quantity: " + quantity);
                size += quantity;
                totalSum += quantity * rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cartSize = size;
        logger.debug("CartDao.getProductsInCart: SUMMARY quantity " + size);
        logger.debug("CartDao.getProductsInCart: totalSum " + totalSum);
        return productsMap;
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
}
