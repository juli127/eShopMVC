package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.hibernate.EntityManagerFactoryUtil;
import com.gmail.kramarenko104.model.Product;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Repository
@DynamicUpdate
//@EnableTransactionManagement
public class ProductDaoImpl implements ProductDao {

//    @Autowired
    private SessionFactory sessionFactory;
    private final static String GET_ALL_PRODUCTS = "from Product p";
    private final static String GET_PRODUCT_BY_CATEGORY = "from Product p where p.category = :category";
    private final static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
//    private Session session;
    private EntityManagerFactory emf;

//    @Autowired
//    public ProductDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public ProductDaoImpl() {
//        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        emf = EntityManagerFactoryUtil.getEntityManagerFactory();
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
    public long updateProduct(Product updPoduct) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long productId = -1;
        try {
            tx.begin();
            productId = updPoduct.getProductId();
            Product dbProduct = em.find(Product.class, productId);
            dbProduct.setName(updPoduct.getName());
            dbProduct.setImage(updPoduct.getImage());
            dbProduct.setCategory(updPoduct.getCategory());
            dbProduct.setDescription(updPoduct.getDescription());
            dbProduct.setPrice(updPoduct.getPrice());
            tx.commit();
        } catch (Exception ex){
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return productId;
    }

    @Override
    public Product getProduct(long productId) {
        EntityManager em = emf.createEntityManager();
        Product product = em.find(Product.class, productId);
        em.close();
        return product;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAllProducts() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery(GET_ALL_PRODUCTS);
        List<Product> productsList = query.getResultList();
        em.close();
        return productsList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProductsByCategory(int category) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery(GET_PRODUCT_BY_CATEGORY);
        List<Product> productsList = query
                .setParameter("category", category )
                .getResultList();
        em.close();
        return productsList;
    }

    @Override
    public void deleteProduct(long productId) {
        EntityManager em = emf.createEntityManager();
        long id = -1;
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Product product = em.find(Product.class, productId);
            if (product != null) {
                em.remove(product);
                tx.commit();
            }
        } catch (Exception ex){
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
