package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.hibernate.EntityManagerFactoryUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDao<T>{

    private Class<T> persistenceClass;
    private EntityManagerFactory emf;

    BaseDao(){
        emf = EntityManagerFactoryUtil.getEntityManagerFactory();
        persistenceClass = (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public boolean isDbConnected(){
        return emf.isOpen();
    }

    public T get(long id) {
        EntityManager em = emf.createEntityManager();
        T t = em.find(persistenceClass, id);
        em.close();
        return t;
    }

    public T update(T newT) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        T updT = null;
        try {
            tx.begin();
            updT = em.merge(newT);
            tx.commit();
        } catch (Exception ex){
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return updT;
    }

    public void delete(long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T t = em.find(persistenceClass, id);
            if (t != null) {
                em.remove(t);
            }
            tx.commit();
        } catch (Exception ex){
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<T> getAll() {
        String GET_ALL = "from " + persistenceClass.getSimpleName() + " t";
        EntityManager em = emf.createEntityManager();
        List<T> resultList = em.createQuery(GET_ALL).getResultList();
        em.close();
        return resultList;
    }
}