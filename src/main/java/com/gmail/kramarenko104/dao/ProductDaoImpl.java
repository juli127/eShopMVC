package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository
//@EnableTransactionManagement
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SessionFactory sessionFactory;
    private final static String ENTITY_NAME = "Product";
    private final static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
    private Session session;

//    @Autowired
//    public ProductDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public ProductDaoImpl() {
    }

//    @Transactional
    @Override
    public int createProduct(Product product) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Serializable id = session.save(ENTITY_NAME, product);
        session.flush();
        session.getTransaction().commit();
        session.close();
        return (int) id;
    }

//    @Transactional
    @Override
    public int updateProduct(Product product) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(ENTITY_NAME, product);
        session.flush();
        int identifier = (int) session.getIdentifier(session);
        session.getTransaction().commit();
        session.close();
        return identifier;
    }

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Product getProduct(int productId) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = (Product) session.get(Product.class, productId);
        session.getTransaction().commit();
        session.close();
        return product;
    }

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAllProducts() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        String hqlString = "select p from " + ENTITY_NAME + " p";
        Query query = session.createQuery(hqlString);
        List<Product> productsList = query.list();
        session.getTransaction().commit();
        session.close();
        return productsList;
    }

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProductsByCategory(int category) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from " + ENTITY_NAME + " p where p.category = :category");
        List<Product> productsList = query.setParameter("category", category ).getResultList();
        session.getTransaction().commit();
        session.close();
        return productsList;
    }

//    @Transactional
    @Override
    public int deleteProduct(int productId) {
        session = sessionFactory.openSession();
        int identifier = -1;
        session.beginTransaction();
        Product product = session.get(Product.class, productId);
        if (product != null) {
            session.delete(ENTITY_NAME, product);
            session.flush();
            identifier = (int) session.getIdentifier(product);
        }
        session.getTransaction().commit();
        session.close();
        return identifier;
    }

    public boolean sessionIsOpen() {
        return sessionFactory.isOpen();
    }
}
