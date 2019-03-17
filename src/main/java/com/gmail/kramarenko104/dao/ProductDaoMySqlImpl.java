package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoMySqlImpl implements ProductDao {

    private static Logger logger = Logger.getLogger(ProductDaoMySqlImpl.class);
    private final static String GET_ALL_PRODUCTS = "SELECT * FROM products;";
    private final static String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?;";
    private final static String GET_PRODUCTS_BY_CATEGORY = "SELECT * FROM products WHERE category = ?;";
    private final static String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?;";
    private Connection conn;
    private List<Product> allProductsList;

    public ProductDaoMySqlImpl(Connection conn) {
        this.conn = conn;
        allProductsList = new ArrayList<>();
    }

    @Override
    public boolean addProduct(Product product) {
        return false;
    }

    @Override
    public Product getProduct(int productId) {
        Product product = new Product();
        ResultSet rs = null;
        try (PreparedStatement ps = conn.prepareStatement(GET_PRODUCT_BY_ID)) {
             ps.setInt(1, productId);
             rs = ps.executeQuery();
            while (rs.next()) {
                fillProduct(rs, product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    private void fillProduct(ResultSet rs, Product product) throws SQLException {
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getInt("category"));
        product.setImage(rs.getString("image"));
    }

    @Override
    public List<Product> getAllProducts() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_PRODUCTS)) {
            while (rs.next()) {
                Product product = new Product();
                fillProduct(rs, product);
                allProductsList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allProductsList;
    }


    @Override
    public List<Product> getProductsByCategory(int category) {
        List<Product> productsList = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = conn.prepareStatement(GET_PRODUCTS_BY_CATEGORY)) {
            ps.setInt(1, category);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                fillProduct(rs, product);
                productsList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsList;
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_PRODUCT)) {
            pst.setInt(1, productId);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
