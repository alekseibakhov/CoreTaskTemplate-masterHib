package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = new Util().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS UsersTable\n" +
                "( id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "  name char(50) NOT NULL,\n" +
                "  lastName char(50),\n" +
                "  age int\n" +
                ") ;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("Таблица UsersTable создана");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    public void dropUsersTable() {
        Statement statement = null;
        String sqlDel = "DROP TABLE IF EXISTS UsersTable";

        try {
            statement = connection.createStatement();
            statement.execute(sqlDel);
            System.out.println("Удалена таблица UsersTable");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    public void saveUser(String name, String lastName, byte age) {
        createUsersTable();

        PreparedStatement preparedStatement = null;

        String sqlSave = "INSERT INTO UsersTable (name, lastName, age) VALUES (?, ?, ?)";

        try {

            preparedStatement = connection.prepareStatement(sqlSave);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " добавлен");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        String sqlRem = "DELETE FROM UsersTable WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlRem);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Удален пользователь с ID = " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        createUsersTable();
        List<User> list = new ArrayList();
        String sqrGet = "SELECT id, name, lastName, age FROM UsersTable";

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqrGet);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                list.add(user);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return list;
    }

    public void cleanUsersTable() {
        createUsersTable();
        String sqlClean = "TRUNCATE TABLE userstable";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(sqlClean);
            System.out.println("Таблица очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }
}
