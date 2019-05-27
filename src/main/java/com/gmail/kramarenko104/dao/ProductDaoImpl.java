package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Product;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@DynamicUpdate
//@EnableTransactionManagement
public class ProductDaoImpl extends BaseDao<Product> implements ProductDao {

    private final static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    @Autowired
    private EntityManagerFactory emf;

    public ProductDaoImpl() {
    }

    @Override
    public long createProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long productId = -1;
        try {
            tx.begin();
            em.persist(product);
            tx.commit();
            productId = product.getProductId();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return productId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProductsByCategory(int category) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Product> query = em.createNamedQuery("GET_PRODUCT_BY_CATEGORY", Product.class)
                .setParameter("category", category);
        List<Product> productsList = query.getResultList();
        em.close();
        return productsList;
    }

}
