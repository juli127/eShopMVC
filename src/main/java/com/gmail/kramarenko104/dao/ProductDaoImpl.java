package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public int createProduct(Product product) {
        session = sessionFactory.openSession();
        Serializable id = session.save("Product", product);
        session.close();
        return (int) id;
    }

    @Transactional
    @Override
    public int updateProduct(Product product) {
        session = sessionFactory.openSession();
        session.update("Product", product);
        int id = (int) session.getIdentifier(session);
        session.close();
        return id;
    }

    @Transactional
    @Override
    public Product getProduct(int productId) {
        session = sessionFactory.openSession();
        Product product = (Product) session.get(Product.class, productId);
        session.close();
        return product;
    }

    @Transactional
    @Override
    public List<Product> getAllProducts() {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Product> productsList = session.createQuery("from Product").list();
        session.close();
        return productsList;
    }

    @Transactional
    @Override
    public List<Product> getProductsByCategory(int category) {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Product> productsList = session.createQuery("select p from Product p where p.category = :category").getResultList();
        session.close();
        return productsList;
    }

    @Transactional
    @Override
    public int deleteProduct(int productId) {
        session = sessionFactory.openSession();
        Product product = session.load(Product.class, productId);
        session.delete("Product", product);
        int id = (int) session.getIdentifier(product);
        session.close();
        return id;
    }

    public boolean sessionIsOpen() {
        return sessionFactory.isOpen();
    }
}
