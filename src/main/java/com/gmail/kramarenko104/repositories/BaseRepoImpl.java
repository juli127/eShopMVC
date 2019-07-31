package com.gmail.kramarenko104.repositories;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseRepoImpl<T> implements BaseRepo<T> {

    private Class<T> persistenceClass;

    @PersistenceContext
    private EntityManager em;

    public BaseRepoImpl() {
        persistenceClass = (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T get(long id) {
        T t = em.find(persistenceClass, id);
        return t;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public T update(T newT) {
        T updT = null;
        updT = em.merge(newT);
        return updT;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void delete(long id) {
        T t = em.find(persistenceClass, id);
        if (t != null) {
            em.remove(t);
        }
    }

    public List<T> getAll() {
        String GET_ALL = "from " + persistenceClass.getSimpleName() + " t";
        List<T> resultList = em.createQuery(GET_ALL).getResultList();
        return resultList;
    }
}
