package server.issuetracker.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.issuetracker.bean.issue.IssueDataSet;

public class HibernateDBService implements DataBaseService {
    private static final String SHOW_SQL = "true";
    private static final String DDL = "create";
    
    private static final Logger log = LoggerFactory.getLogger(HibernateDBService.class);
    
    private final SessionFactory sessionFactory;
    
    public HibernateDBService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public void printConnectionInfo() {
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        try {
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            log.info("DB name: " + connection.getMetaData().getDatabaseProductName());
            log.info("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            log.info("Driver: " + connection.getMetaData().getDriverName());
            log.info("Autocommit status: " + connection.getAutoCommit());
        } catch (SQLException e) {
            log.error("problems with getting connection details");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        Connection connection = null;
        try {
            connection = sessionFactoryImpl.getConnectionProvider().getConnection();
        } catch (SQLException e) {
            log.error("problem with getting connection");
            e.printStackTrace();
        }
        return connection;
    }
    
    
    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(IssueDataSet.class);
        
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./database");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", DDL);
        return configuration;
    }
    
    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
