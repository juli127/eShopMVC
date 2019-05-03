package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.ProductDaoImpl;
import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoImpl productDao;

    public long addProduct(Product product) {
        return productDao.createProduct(product);
    }

    public Product getProduct(long id) {
        return productDao.getProduct(id);
    }

    public long deleteProduct(long id) {
        return productDao.deleteProduct(id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public List<Product> getProductsByCategory(int category) {
        return productDao.getProductsByCategory(category);
    }

    public Session openSession() {
        return productDao.getSessionFactory().openSession();
    }

    public void closeSession(){
        productDao.getSessionFactory().getCurrentSession().close();
    }

    public void setProductDao(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }
}
