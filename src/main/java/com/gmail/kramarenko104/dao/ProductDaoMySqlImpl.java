package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.models.Product;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoMySqlImpl implements ProductDao {
    /*  private int id;
   private String name;
   private int category;
   private int price;
   private String description;
   private File image;
*/
    private final static String GET_ALL_PRODUCTS = "SELECT * FROM products;";
    private final static String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?;";
    private final static String GET_PRODUCTS_BY_CATEGORY = "SELECT * FROM products WHERE category = ?;";

    private Connection conn;

    public ProductDaoMySqlImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean addProduct(Product product) {
        return false;
    }

    @Override
    public Product getProduct(int id) {
        Product product = new Product();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_PRODUCT_BY_ID)) {
            while (rs.next()) {
                fillProduct(rs, product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Product editProduct(int id, Product user) {
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> allProductsList = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_PRODUCTS)) {
            System.out.println("GET_ALL_PRODUCTS = " + GET_ALL_PRODUCTS);
            System.out.println("has products? = " + rs.isBeforeFirst());
            while (rs.next()) {
                Product product = new Product();
                allProductsList.add(fillProduct(rs, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allProductsList;
    }

    private Product fillProduct(ResultSet rs, Product product) throws SQLException {
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getInt("category"));
        product.setImage(rs.getString("image"));
        return product;
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
        } finally {
            closeResultSet(rs);
        }
        return productsList;
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }

    public List<String> getCategoriesListS() {
        List<String> allCategoriesList = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT name FROM categories;")) {
            while (rs.next()) {
                String category = rs.getString(1);
                System.out.println("contr: " + category);
                allCategoriesList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCategoriesList;
    }

    // save picture into BLOB field of DB
    public void insertImage(FileInputStream fis) throws SQLException {
        String query = "INSERT INTO Picture (picture) VALUES (?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setBinaryStream(1, fis);
        pstmt.executeUpdate();
    }

}
