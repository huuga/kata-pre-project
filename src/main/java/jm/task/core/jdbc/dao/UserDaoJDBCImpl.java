package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.CallableStatement;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String TABLE_NAME = "users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        StringBuilder tableContent = new StringBuilder();
        tableContent.append("(user_id BIGINT PRIMARY KEY AUTO_INCREMENT, ")
                .append("name VARCHAR(20), ")
                .append("last_name VARCHAR(30), ")
                .append("age TINYINT);");


        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate("CREATE TABLE if NOT EXISTS " + TABLE_NAME + tableContent);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate("DROP TABLE if EXISTS " + TABLE_NAME);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(TABLE_NAME)
                .append(" (name, last_name, age) VALUES ('")
                .append(name).append("', '")
                .append(lastName).append("', ")
                .append(age).append(");");

        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate(sql.toString());
            System.out.println("User с именем - '" + name + " " + lastName + "' добавлен в базу данных.");

        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ")
                .append(TABLE_NAME)
                .append(" WHERE user_id=")
                .append(id).append(";");

        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate(sql.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * from ").append(TABLE_NAME);

        try (Statement statement = Util.getConnection().createStatement()){
            ResultSet rs = statement.executeQuery(sql.toString());

            while (rs.next()) {
                User nextUser = new User(rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4));
                nextUser.setId(rs.getLong(1));
                result.add(nextUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void cleanUsersTable() {

        StringBuilder sql = new StringBuilder();
        sql.append("TRUNCATE TABLE ").append(TABLE_NAME).append(";");

        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate(sql.toString());
            System.out.println("Таблица '" + TABLE_NAME + "' очищена.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
