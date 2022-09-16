package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getMySQLConnection();
             Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE TABLE USERS " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        };
    }

    public void dropUsersTable() {
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            String sql = "DROP TABLE IF EXISTS USERS";
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            String sql = "INSERT INTO Users VALUES (0, '" + name + "', '"
                    + lastName + "', '" + age + "')";
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    public void removeUserById(long id) {
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            String sql = "DELETE FROM USERS where id = " + id;
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            String sql = "SELECT * FROM USERS";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                list.add(new User(name, lastName, age));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
        return list;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            String sql = "TRUNCATE TABLE USERS";
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }
}
