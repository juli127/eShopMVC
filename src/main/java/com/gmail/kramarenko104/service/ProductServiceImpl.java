package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.ProductDaoImpl;
import com.gmail.kramarenko104.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoImpl productDao;

    public int addProduct(Product product) {
        return productDao.createProduct(product);
    }

    public Product getProduct(int id) {
        return productDao.getProduct(id);
    }

    public int deleteProduct(int id) {
        return productDao.deleteProduct(id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public List<Product> getProductsByCategory(int category) {
        return productDao.getProductsByCategory(category);
    }

    public boolean sessionIsOpen() {
        return productDao.sessionIsOpen();
    }
}
