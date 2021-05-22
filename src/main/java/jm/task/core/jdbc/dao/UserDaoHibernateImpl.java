package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS UsersTable\n" +
                    "( id int PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  name char(50) NOT NULL,\n" +
                    "  lastName char(50),\n" +
                    "  age int\n" +
                    ") ;";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Табл создана");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {

            session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            String sqlDel = "DROP TABLE IF EXISTS UsersTable";
            session.createSQLQuery(sqlDel).executeUpdate();
            transaction.commit();
            System.out.println("Табл удалена");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Юзер создан");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {

            session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("delete from users_db.UsersTable where id = " + id).executeUpdate();
            transaction.commit();
            System.out.println("Юзер удален");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override

    public List<User> getAllUsers() {
        createUsersTable();
        List<User> result = null;
        try {

            session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            result = (List<User>) Util.getConnectionHibernate().openSession().createQuery("From User").list();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {

        try {
            createUsersTable();
            String sqlClean = "TRUNCATE TABLE userstable";

            session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(sqlClean).executeUpdate();
            transaction.commit();
            System.out.println("Табл очищена");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

}