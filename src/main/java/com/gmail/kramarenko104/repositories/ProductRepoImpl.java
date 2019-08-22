package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductRepoImpl extends BaseRepoImpl<Product> implements ProductRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public long createProduct(Product product) {
        em.persist(product);
        long productId = product.getProductId();
        return productId;
    }

    @Override
    public List<Product> getProductsByCategory(int category) {
        TypedQuery<Product> query = em.createNamedQuery("GET_PRODUCT_BY_CATEGORY", Product.class)
                .setParameter("category", category);
        List<Product> productsList = query.getResultList();
        return productsList;
    }
}
