package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/users_schema";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "fkFDSfwejk&234";

    public static Connection getConnection() {

        Connection connection;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Соединение с БД установлено.");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
