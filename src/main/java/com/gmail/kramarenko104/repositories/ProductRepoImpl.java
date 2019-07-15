package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Product;
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
public class ProductRepoImpl extends BaseRepoImpl<Product> implements ProductRepo {

    private final static Logger logger = LoggerFactory.getLogger(ProductRepoImpl.class);
    private EntityManagerFactory emf;

    @Autowired
    public ProductRepoImpl(EntityManagerFactory emf) {
        this.emf = emf;
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
    public List<Product> getProductsByCategory(int category) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Product> query = em.createNamedQuery("GET_PRODUCT_BY_CATEGORY", Product.class)
                .setParameter("category", category);
        List<Product> productsList = query.getResultList();
        em.close();
        return productsList;
    }
}
