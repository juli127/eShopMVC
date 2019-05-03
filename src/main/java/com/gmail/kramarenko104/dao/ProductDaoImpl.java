package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.factoryDao.HibernateSessionFactoryUtil;
import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
@DynamicUpdate
//@EnableTransactionManagement
public class ProductDaoImpl implements ProductDao {

//    @Autowired
    private SessionFactory sessionFactory;
    private final static String ENTITY_NAME = "Product";
    private final static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
    private Session session;

//    @Autowired
//    public ProductDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public ProductDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

//    @Transactional
    @Override
    public long createProduct(Product product) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Serializable id = session.save(ENTITY_NAME, product);
        session.flush();
        tx.commit();
        session.close();
        return (long) id;
    }

//    @Transactional
    @Override
    public long updateProduct(Product product) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(ENTITY_NAME, product);
        session.flush();
        long identifier = (int) session.getIdentifier(session);
        tx.commit();
        session.close();
        return identifier;
    }

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Product getProduct(long productId) {
        session = sessionFactory.openSession();
        Product product = (Product) session.get(Product.class, productId);
        session.close();
        return product;
    }

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAllProducts() {
        session = sessionFactory.openSession();
        Query query = session.createQuery("select p from " + ENTITY_NAME + " p");
        List<Product> productsList = query.list();
        session.close();
        return productsList;
    }

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProductsByCategory(int category) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("select p from " + ENTITY_NAME + " p where p.category = :category");
        List<Product> productsList = query
                .setParameter("category", category )
                .getResultList();
        session.close();
        return productsList;
    }

//    @Transactional
    @Override
    public long deleteProduct(long productId) {
        session = sessionFactory.openSession();
        long identifier = -1;
        Transaction tx = session.beginTransaction();
        Product product = session.get(Product.class, productId);
        if (product != null) {
            session.delete(ENTITY_NAME, product);
            session.flush();
            identifier = (int) session.getIdentifier(product);
        }
        tx.commit();
        session.close();
        return identifier;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
