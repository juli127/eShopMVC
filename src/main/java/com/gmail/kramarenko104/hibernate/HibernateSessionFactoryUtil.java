package com.gmail.kramarenko104.hibernate;

import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(Cart.class);
            configuration.addAnnotatedClass(Product.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("There was en error building the factory: " + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}