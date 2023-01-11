package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private String TABLE_NAME = "users";
    private String hiberSeqFirstRec = "INSERT INTO hibernate_sequence (next_val) VALUES (1);";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        StringBuilder tableContent = new StringBuilder();
        tableContent.append(" (user_id BIGINT PRIMARY KEY AUTO_INCREMENT, ")
                .append("name VARCHAR(20), ")
                .append("last_name VARCHAR(30), ")
                .append("age TINYINT);");
        String createHiberSeq = "CREATE TABLE if NOT EXISTS hibernate_sequence (next_val BIGINT) engine=InnoDB;";


        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createSQLQuery("CREATE TABLE if NOT EXISTS " + TABLE_NAME + tableContent).executeUpdate();
            session.createSQLQuery(createHiberSeq).executeUpdate();
            Query query = session.createSQLQuery("SELECT next_val FROM hibernate_sequence;");
            if (query.list().size() == 0) {
                session.createSQLQuery(hiberSeqFirstRec).executeUpdate();
            }
            session.getTransaction();

        } catch (Exception ignored) {

        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS " + TABLE_NAME + ";").executeUpdate();
            session.createSQLQuery("DROP TABLE if EXISTS hibernate_sequence;").executeUpdate();
            session.getTransaction();

        } catch (Exception ignored) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction tx = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            session.saveOrUpdate(new User(name, lastName, age));
            tx = session.beginTransaction();
            tx.commit();
            System.out.println("User с именем - '" + name + " " + lastName + "' добавлен в базу данных.");

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {

            User removingUser = new User();
            removingUser.setId(id);
            session.delete(removingUser);

        } catch (Exception e) {

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            users = (List<User>) session.createQuery("from User").list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }


    @Override
    public void cleanUsersTable() {
        Transaction tx = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.createQuery("DELETE from User").executeUpdate();
            session.createSQLQuery("TRUNCATE TABLE hibernate_sequence;").executeUpdate();
            session.createSQLQuery(hiberSeqFirstRec).executeUpdate();
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
