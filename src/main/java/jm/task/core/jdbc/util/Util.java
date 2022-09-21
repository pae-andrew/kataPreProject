package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Util {
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {

        String hostName = "localhost";
        String dbName = "sys";
        String userName = "root";
        String password = "Pae551255";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
                                                                                        ClassNotFoundException {

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }

    public static Session getMySQLSession() {
        String hostName = "localhost";
        String dbName = "sys";
        String userName = "root";
        String password = "Pae551255";

        return getMySQLSession(hostName, dbName, userName, password);
    }

    public static Session getMySQLSession(String hostName, String dbName,
                                       String userName, String password) {
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://" + hostName + ":3306/" + dbName);
        prop.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");

        prop.setProperty("hibernate.connection.username", userName);
        prop.setProperty("hibernate.connection.password", password);
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("show_sql", "true");
        SessionFactory sessionFactory = new Configuration().addProperties(prop).buildSessionFactory();
        return sessionFactory.openSession();
    }

    public static SessionFactory getCurrentSession() {
        String hostName = "localhost";
        String dbName = "sys";
        String userName = "root";
        String password = "Pae551255";

        return getCurrentSession(hostName, dbName, userName, password);
    }

    public static SessionFactory getCurrentSession(String hostName, String dbName,
                                            String userName, String password) {
        // Hibernate 5.4 SessionFactory example without XML
        Map<String, String> settings = new HashMap<>();
//        settings.put("connection.provider_class", "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
//        settings.put("hibernate.c3p0.acquire_increment", "1");
//        settings.put("hibernate.c3p0.idle_test_period", "60");
//        settings.put("hibernate.c3p0.min_size", "1");
//        settings.put("hibernate.c3p0.max_size", "2");
//        settings.put("hibernate.c3p0.max_statements", "50");
//        settings.put("hibernate.c3p0.timeout", "0");
//        settings.put("hibernate.c3p0.acquireRetryAttempts", "1");
//        settings.put("hibernate.c3p0.acquireRetryDelay", "250");

        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url", "jdbc:mysql://" + hostName + ":3306/" + dbName);
        settings.put("hibernate.connection.username", userName);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        settings.put("hibernate.use_sql_comments", "true");

        settings.put("hibernate.transaction.coordinator_class", "org.hibernate.transaction.JDBCTransactionFactory");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        // metadataSources.addAnnotatedClass(Player.class);
        Metadata metadata = metadataSources.buildMetadata();

        // here we build the SessionFactory (Hibernate 5.4)
        return metadata.getSessionFactoryBuilder().build();
    }
}
