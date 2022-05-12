package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            Session session = null;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS `new_db`.`user` (`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,`name` VARCHAR(20) NOT NULL,`lastName` VARCHAR(20) NOT NULL,`age` INT NOT NULL);";
            session.createSQLQuery(SQL).executeUpdate();

            session.getTransaction().commit();

            session.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = null;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS `new_db`.`user`";
            session.createSQLQuery(SQL).executeUpdate();

            session.getTransaction().commit();

            session.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = null;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();

            session.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try{
            Session session = null;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User userFromDB = session.get(User.class, id);
            session.delete(userFromDB);
            session.getTransaction().commit();

            session.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userBD = new ArrayList<>();
        try{
            Session session = null;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            userBD = session.createQuery("FROM User").getResultList();
            System.out.println(userBD.toString());

            session.getTransaction().commit();

            session.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return userBD;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = null;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            session.getTransaction().commit();

            session.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
