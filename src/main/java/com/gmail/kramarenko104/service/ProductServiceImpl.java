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

    public ProductServiceImpl(){
    }

    @Override
    public long createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public Product getProduct(long productId) {
        return productDao.get(productId);
    }

    @Override
    public void deleteProduct(long productId) {
        productDao.delete(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    @Override
    public List<Product> getProductsByCategory(int category) {
        return productDao.getProductsByCategory(category);
    }

}
