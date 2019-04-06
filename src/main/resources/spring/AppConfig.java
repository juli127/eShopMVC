package spring;

import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import javax.sql.DataSource;

@Configuration
//@ComponentScan("com.gmail.kramarenko104")
//@ImportResource(locations = "../../webapp/WEB-INF/applicationContext.xml")
//@PropertySource(name="classpath:spring/application.properties")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class AppConfig {

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", "${hibernate.dialect}");
        properties.put("hibernate.connection.driver_class", "${hibernate.connection.driver_class}");
        properties.put("hibernate.connection.url", "${hibernate.connection.url}");
        properties.put("hibernate.connection.username", "${hibernate.connection.username}");
        properties.put("hibernate.connection.password", "${hibernate.connection.password}");
        properties.put("show_sql", "${hibernate.show_sql}");
        properties.put("hbm2ddl.auto", "${hibernate.hbm2ddl.auto}");
        return properties;
    }

    @Bean
    private DataSource dataSource() {
        DataSource ds;
        ds.setMaximumPoolSize(100);
        ds.setDataSourceClassName("${db.driverClassName}");
        ds.addDataSourceProperty("url", "${db.url}");
        ds.addDataSourceProperty("user", "${db.username}");
        ds.addDataSourceProperty("password", "${db.password}");
        ds.addDataSourceProperty("cachePrepStmts", "${db.cachePrepStmts}");
        ds.addDataSourceProperty("prepStmtCacheSize", "${db.prepStmtCacheSize}");
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", "${db.prepStmtCacheSqlLimit}");
        ds.addDataSourceProperty("useServerPrepStmts", "${db.useServerPrepStmts}");
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        PlatformTransactionManager txManager = new PlatformTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }


}

