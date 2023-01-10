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
            System.out.println("Создание таблицы '" + TABLE_NAME + "'...");
            statement.executeUpdate("CREATE TABLE " + TABLE_NAME + tableContent);
            System.out.println("Таблица '" + TABLE_NAME + "' создана.");
            System.out.println();

        } catch (SQLException e) {
            if (e.getMessage().equals("Table '" + TABLE_NAME + "' already exists")){
                System.out.println("ERROR: " + e.getMessage());
                System.out.println();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {

        try (Statement statement = Util.getConnection().createStatement()){
            System.out.println("Удаление таблицы '" + TABLE_NAME + "'...");
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);
            System.out.println("Таблица '" + TABLE_NAME + "' удалена.");
            System.out.println();

        } catch (SQLException e) {
            if (e.getMessage().equals("Unknown table 'users_schema." + TABLE_NAME + "'")){
                System.out.println("ERROR: " + e.getMessage());
                System.out.println();
            } else {
                e.printStackTrace();
            }
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
            System.out.println("Добавление '" + name + " " + lastName + "' в базу данных...");
            statement.executeUpdate(sql.toString());
            System.out.println("User с именем - '" + name + " " + lastName + "' добавлен в базу данных.");
            System.out.println();

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
            System.out.println("Удаление user с id " + id + "...");
            statement.executeUpdate(sql.toString());
            System.out.println("User c id " + id + " удален.");
            System.out.println();

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
        sql.append("DELETE from ").append(TABLE_NAME);

        try (Statement statement = Util.getConnection().createStatement()){
            System.out.println("Очистка таблицы '" + TABLE_NAME + "'...");
            statement.executeUpdate(sql.toString());
            System.out.println("Таблица '" + TABLE_NAME + "' очищена.");
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
