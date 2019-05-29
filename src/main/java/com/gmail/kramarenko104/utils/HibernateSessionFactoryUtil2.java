package com.gmail.kramarenko104.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
public class HibernateSessionFactoryUtil2 {

    private static SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try {
            // MetadataSources - for work with metadata objects' mapping
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory,
            // but we had trouble building the SessionFactory, so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

