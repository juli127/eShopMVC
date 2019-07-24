package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductRepoImpl extends BaseRepoImpl<Product> implements ProductRepo {

    private final static Logger logger = LoggerFactory.getLogger(ProductRepoImpl.class);
    EntityManagerFactory emf;

    @Autowired
    public ProductRepoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = Exception.class)
    public long createProduct(Product product) {
        long productId = -1;
        EntityManager em = emf.createEntityManager();
        try {
            em.persist(product);
            productId = product.getProductId();
        } catch (Exception ex) {
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
