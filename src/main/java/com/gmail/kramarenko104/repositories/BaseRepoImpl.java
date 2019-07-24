package com.gmail.kramarenko104.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseRepoImpl<T> implements BaseRepo<T> {

    private Class<T> persistenceClass;

    @Autowired
    private EntityManagerFactory emf;

    BaseRepoImpl(){
        persistenceClass = (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T get(long id) {
        EntityManager em = emf.createEntityManager();
        T t = em.find(persistenceClass, id);
        em.close();
        return t;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
                    isolation = Isolation.SERIALIZABLE,
                    rollbackFor = Exception.class)
    public T update(T newT) {
        EntityManager em = emf.createEntityManager();
        T updT = null;
        try {
            updT = em.merge(newT);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return updT;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = Exception.class)
    public void delete(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            T t = em.find(persistenceClass, id);
            if (t != null) {
                em.remove(t);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<T> getAll() {
        EntityManager em = emf.createEntityManager();
        String GET_ALL = "from " + persistenceClass.getSimpleName() + " t";
        List<T> resultList = em.createQuery(GET_ALL).getResultList();
        em.close();
        return resultList;
    }
}
