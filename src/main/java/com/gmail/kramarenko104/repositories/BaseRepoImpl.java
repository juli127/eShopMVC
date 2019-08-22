package com.gmail.kramarenko104.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class BaseRepoImpl<T> implements BaseRepo<T> {

    private Class<T> persistenceClass;
    private static Logger logger = LoggerFactory.getLogger(BaseRepoImpl.class);

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
        try {
            T t = Optional.ofNullable(em.find(persistenceClass, id))
                    .orElseThrow(() -> new EntityNotFoundException("Not found instance of "
                            + persistenceClass.getSimpleName() + " for id=" + id));
            em.remove(t);
        } catch (EntityNotFoundException ex) {
            logger.debug(ex.getMessage());
        }
    }

    public List<T> getAll() {
        String GET_ALL = "from " + persistenceClass.getSimpleName() + " t";
        List<T> resultList = em.createQuery(GET_ALL).getResultList();
        return resultList;
    }
}
