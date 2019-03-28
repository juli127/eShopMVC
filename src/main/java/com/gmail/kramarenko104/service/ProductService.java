package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.ProductDaoImpl;
import com.gmail.kramarenko104.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDaoImpl productDao;

    // CRUD functionality
    public boolean addProduct(Product product){
        return productDao.addProduct(product);
    }

    public Product getProduct(int id){
        return productDao.getProduct(id);
    }

    public boolean deleteProduct(int id){
        return productDao.deleteProduct(id);
    }

    public List<Product> getAllProducts(){
        return productDao.getAllProducts();
    }

    public List<Product> getProductsByCategory(int category){
        return productDao.getProductsByCategory(category) ;
    }

    public boolean sessionIsOpen() {
        return productDao.sessionIsOpen();
    }
}
