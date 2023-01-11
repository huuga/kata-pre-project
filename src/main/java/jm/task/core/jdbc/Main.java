package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        UserService uService = new UserServiceImpl();

        uService.createUsersTable();
        uService.saveUser("Narko", "Mirando", (byte) 43);
        uService.saveUser("Narko", "Mirando", (byte) 12);
        uService.saveUser("Narko", "Mirando", (byte) 10);
        uService.saveUser("Narko", "Mirando", (byte) 10);
        uService.removeUserById(1);
        uService.getAllUsers().forEach(System.out::println);
        uService.cleanUsersTable();
        uService.dropUsersTable();

    }
}
