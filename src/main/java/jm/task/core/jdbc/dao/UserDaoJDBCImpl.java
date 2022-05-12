package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static int USER_COUNT;


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try ( Connection connection = Util.getConnection();
                Statement statement = connection.createStatement() ) {

            String SQL = "CREATE TABLE IF NOT EXISTS `new_db`.`user` (\n" +
                    " `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    " `name` VARCHAR(20) NOT NULL,\n" +
                    " `lastName` VARCHAR(20) NOT NULL,\n" +
                    " `age` INT NOT NULL);";
            statement.executeUpdate(SQL);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try ( Connection connection = Util.getConnection();
              Statement statement = connection.createStatement() ) {

            String SQL = "DROP TABLE IF EXISTS `new_db`.`user`";
            statement.executeUpdate(SQL);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try ( Connection connection = Util.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES(?, ?, ?)") ) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try ( Connection connection = Util.getConnection();
              Statement statement = connection.createStatement() ) {

            String SQL = "DELETE FROM user WHERE id = " + id;
            statement.executeUpdate(SQL);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> userBD = new ArrayList<>();
        try ( Connection connection = Util.getConnection();
              Statement statement = connection.createStatement() ) {

            String SQL = "SELECT * FROM user";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userBD.add(user);
                System.out.println(user.toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userBD;
    }

    public void cleanUsersTable() {
        try ( Connection connection = Util.getConnection();
              Statement statement = connection.createStatement() ) {

            String SQL = "TRUNCATE TABLE user";
            statement.executeUpdate(SQL);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
