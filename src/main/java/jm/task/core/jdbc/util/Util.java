package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/users_schema";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "fkFDSfwejk&234";
    private static final String HB_DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry standardServiceRegistry;

    public static Connection getConnection() {

        Connection connection;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                Map<String, String> dbSettings = new HashMap<>();
                dbSettings.put(Environment.URL, DB_URL);
                dbSettings.put(Environment.USER, DB_USERNAME);
                dbSettings.put(Environment.PASS, DB_PASSWORD);
                dbSettings.put(Environment.DRIVER, DB_DRIVER);
                dbSettings.put(Environment.DIALECT, HB_DIALECT);
//                dbSettings.put(Environment.SHOW_SQL, "true");

                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
                standardServiceRegistryBuilder.applySettings(dbSettings);
                standardServiceRegistry = standardServiceRegistryBuilder.build();

                MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
                metadataSources.addAnnotatedClass(User.class);

                Metadata metadata = metadataSources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

}
