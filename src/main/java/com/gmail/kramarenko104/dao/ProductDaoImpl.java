package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@EnableTransactionManagement
public class ProductDaoImpl implements ProductDao {

    private final SessionFactory sessionFactory;
    private final String ENTITY_NAME = "Product";
    private Session session;

    @Autowired
    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public int createProduct(Product product) {
        session = sessionFactory.openSession();
        Serializable id = session.save(ENTITY_NAME, product);
        session.flush();
        session.close();
        return (int) id;
    }

    @Transactional
    @Override
    public int updateProduct(Product product) {
        session = sessionFactory.openSession();
        session.update(ENTITY_NAME, product);
        session.flush();
        int identifier = (int) session.getIdentifier(session);
        session.close();
        return identifier;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Product getProduct(int productId) {
        session = sessionFactory.openSession();
        Product product = (Product) session.get(Product.class, productId);
        session.close();
        return product;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Product> getAllProducts() {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Product> productsList = session.createQuery("from " + ENTITY_NAME).list();
        session.close();
        return productsList;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Product> getProductsByCategory(int category) {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Product> productsList = session.createQuery("select p from " + ENTITY_NAME + " p where p.category = :category").getResultList();
        session.close();
        return productsList;
    }

    @Transactional
    @Override
    public int deleteProduct(int productId) {
        session = sessionFactory.openSession();
        Product product = session.get(Product.class, productId);
        session.delete(ENTITY_NAME, product);
        session.flush();
        int identifier = (int) session.getIdentifier(product);
        session.close();
        return identifier;
    }

    public boolean sessionIsOpen() {
        return sessionFactory.isOpen();
    }
}
