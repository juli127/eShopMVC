package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.models.Cart;
import com.gmail.kramarenko104.models.Product;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class CartDaoMySqlImpl implements CartDao {

    private final static String GET_CART_BY_USERID = "SELECT * FROM carts WHERE userId = ?;";
    private final static String ADD_TO_CART = "INSERT INTO carts (userId, productId, quantity) VALUES(?,?,?);";
    private final static String DELETE_CART = "DELETE FROM carts WHERE id = ?;";
    private final static String UPDATE_CART = "UPDATE carts SET quantity = ? WHERE userId =? AND productId = ?;";
    private final static String GET_PRODUCTS = "SELECT * FROM carts WHERE userId =? AND productId = ?;";
    private final static String GET_ALL_FROM_CART = "SELECT products.*, carts.quantity ROM products INNER JOIN carts ON products.id = carts.productId;";

//    private Map<Integer, Integer> products;
    private int size;
    private Connection conn;

    public CartDaoMySqlImpl(Connection conn) {
        this.conn = conn;
//        this.products = new HashMap<>();
        size = 0;
    }

    @Override
    public Cart createCart(int userId) {
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
        return cart;
    }

    @Override
    public Cart getCart(int userId) {
        ResultSet rs = null;
        Cart cart = new Cart();
        try (PreparedStatement pst = conn.prepareStatement(GET_CART_BY_USERID)) {
            pst.setInt(1, userId);
            pst.execute();
            rs = pst.executeQuery();
            while (rs.next()) {
                cart.setUserId(userId);
                int productId = rs.getInt("productId");
                int quantity = rs.getInt("quantity");
                cart.setProductId(productId);
                cart.setQuantity(quantity);
//                products.put(productId, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        return cart;
    }

    @Override
    public void addProduct(int userId, int productId, int quantity) {
        // there is already such product in dB, just update quantity
//        if (productExists(userId, productId)) {
        if (productExists(userId, productId)) {
            try (PreparedStatement pst = conn.prepareStatement(UPDATE_CART)) {
                pst.setInt(1, quantity + getProductQuantity(userId, productId));
                pst.setInt(2, userId);
                pst.setInt(3, productId);
                pst.execute();
//                products.put(productId, products.get(productId) + quantity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else { //there isn't such product in dB, add it
            try (PreparedStatement pst = conn.prepareStatement(ADD_TO_CART)) {
                conn.setAutoCommit(false);
                pst.setInt(1, userId);
                pst.setInt(2, productId);
                pst.setInt(3, quantity);
                pst.execute();
                conn.commit();
                conn.setAutoCommit(true);
//                products.put(productId, quantity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
//        if (getProductsSet().contains(product)){
//            qnt = products.get(product) - 1;
//        }
//        products.put(product, qnt);
//        size--;
    }

    @Override
    public void deleteCart(int userId) {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_CART)) {
            pst.setInt(1, userId);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public int getSize() {
//        return products.size();
//    }

    @Override
    public Map<Product, Integer> getProductsMap() {
        Map<Product, Integer> productsMap = null;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_FROM_CART)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getInt("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("prices"));
                product.setImage(rs.getString("image"));
                productsMap.put(product, rs.getInt("quantity"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
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
