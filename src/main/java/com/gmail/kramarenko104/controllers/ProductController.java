package com.gmail.kramarenko104.controllers;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gmail.kramarenko104.models.Product;

public class ProductController {

    private DBWorker worker;
    private List<Product> allProductsList;
    private List<Integer> allCategoriesList;
    private Connection conn;
    private final static String GET_ALL_PRODUCTS = "SELECT * FROM products;";
    private final static String GET_PRODUCTS_BY_CATEGORY = "SELECT * FROM products WHERE category = ?;";
    private final static String GET_ALL_CATEGORIES = "SELECT DISTINCT category FROM products;";

    public ProductController() {
        worker = new DBWorker();
        allProductsList = new ArrayList<>();
        conn = worker.getConnection();
    }

    /*  private int id;
        private String name;
        private int category;
        private int price;
        private String description;
        private File image;
    */
    public List<Product> getAllProducts() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_PRODUCTS)) {
            while (rs.next()) {
                Product product = getProduct(rs);
                allProductsList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allProductsList;
    }

    // save picture into BLOB field of DB
    public void insertImage(FileInputStream fis) throws SQLException {
        String query = "INSERT INTO Picture (picture) VALUES (?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setBinaryStream(1, fis);
        pstmt.executeUpdate();
    }


    public List<Product> getProductsByCategory(int category) {
        List<Product> productsList = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = conn.prepareStatement(GET_PRODUCTS_BY_CATEGORY)) {
            ps.setInt(1, category);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = getProduct(rs);
                productsList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return productsList;
    }

    private Product getProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getInt("category"));
        //String fullPath = Paths.get(".", "/src/main/resources/images/" + rs.getString("image")).toAbsolutePath().normalize().toString();
        // relative path -- folder with images is located in project root
        product.setImage(rs.getString("image"));
        //String fullPath = "images/" + rs.getString("image");
        //product.setImage(fullPath);
        return product;
    }

    public List<Integer> getCategoriesList() {
        allCategoriesList = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_CATEGORIES)) {
            while (rs.next()) {
                Integer category = rs.getInt("category");
                allCategoriesList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCategoriesList;
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

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // logger.debug("DBWorker: connection was closed");
    }
}
