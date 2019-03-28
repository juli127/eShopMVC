package spring;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    DataSource ds;
        @Bean
        public LocalSessionFactoryBean sessionFactory() {
            LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            sessionFactory.setHibernateProperties(hibernateProperties());
        ...
            return sessionFactory;
        }

        @Bean
        public HibernateTransactionManager transactionManager() {
            HibernateTransactionManager txManager = new HibernateTransactionManager();
            txManager.setSessionFactory(sessionFactory().getObject());
            return txManager;
        }

        private DataSource dataSource() {

            ds.setMaximumPoolSize(100);
            ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            ds.addDataSourceProperty("url", url);
            ds.addDataSourceProperty("user", username);
            ds.addDataSourceProperty("password", password);
            ds.addDataSourceProperty("cachePrepStmts", true);
            ds.addDataSourceProperty("prepStmtCacheSize", 250);
            ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
            ds.addDataSourceProperty("useServerPrepStmts", true);
            return ds;
        }

        private Properties hibernateProperties() {
            final Properties properties = new Properties();
        ... (Dialect, 2nd level entity cache, query cache, etc.)
            return properties;
        }
    }

