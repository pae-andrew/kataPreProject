package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";
        try (Connection conn = Util.getMySQLConnection();
             Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так..." + e.getMessage());
        };
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS";
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users VALUES (?, ?, ?, ?)";
        try(Connection conn = Util.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, 0);
            stmt.setString(2, name);
            stmt.setString(3, lastName);
            stmt.setByte(4, age);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS where id = ?";
        try(Connection conn = Util.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
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
        String sql = "TRUNCATE TABLE USERS";
        try(Connection conn = Util.getMySQLConnection();
            Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так...");
        }
    }
}
