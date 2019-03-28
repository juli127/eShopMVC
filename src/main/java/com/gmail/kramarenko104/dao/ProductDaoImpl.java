package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

    @Override
    public int createProduct(Product product) {
        session = sessionFactory.openSession();
        Serializable id = session.save("Product", product);
        return (int) id;
    }

    @Override
    public int updateProduct(Product product) {
        session = sessionFactory.openSession();
        session.update("Product", product);
        return (int) session.getIdentifier(session);
    }

    @Override
    public Product getProduct(int productId) {
        session = sessionFactory.openSession();
        Product product = (Product) session.get(Product.class, productId);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Product> productsList = session.createQuery("from Product").list();
        return productsList;
    }


    @Override
    public List<Product> getProductsByCategory(int category) {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Product> productsList = session.createQuery("select p from Product p where p.category = :category").getResultList();
        return productsList;
    }

    @Override
    public int deleteProduct(int productId) {
        session = sessionFactory.openSession();
        Product product = session.load(Product.class, productId);
        session.delete("Product", product);
        return (int) session.getIdentifier(product);
    }

    public boolean sessionIsOpen() {
        return sessionFactory.isOpen();
    }
}
