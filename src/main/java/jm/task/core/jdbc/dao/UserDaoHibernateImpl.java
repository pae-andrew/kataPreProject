package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";
        try (SessionFactory sessionFactory = Util.getCurrentSession();
             Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS";
        try(SessionFactory sessionFactory = Util.getCurrentSession();
            Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users VALUES (?, ?, ?, ?)";
        try(SessionFactory sessionFactory = Util.getCurrentSession();
            Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            session.createSQLQuery(sql)
                    .setParameter(1, 0)
                    .setParameter(2, name)
                    .setParameter(3, lastName)
                    .setParameter(4, age)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }

    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS where id = ?";
        try (SessionFactory sessionFactory = Util.getCurrentSession();
             Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            session.createSQLQuery(sql)
                    .setParameter(1, id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try(SessionFactory sessionFactory = Util.getCurrentSession();
            Session session = sessionFactory.openSession()
        ) {
            NativeQuery query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                String name = row[1].toString();
                String lastName = row[2].toString();
                byte age = Byte.parseByte(row[3].toString());
                list.add(new User(name, lastName, age));
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE USERS";
        try (SessionFactory sessionFactory = Util.getCurrentSession();
                Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
