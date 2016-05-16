package server.issuetracker.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.issuetracker.bean.user.User;
import server.issuetracker.db.Executor;
import server.issuetracker.utils.Users;

public class UserRepositoryJdbc implements UserRepository {
    private static final String USER_TABLE = "users";
    private static final String GET_USER_BY_LOGIN = "select * from " + USER_TABLE + " where login='";
    private static final String GET_USER_BY_ID = "select * from " + USER_TABLE + " where id=";
    private static final String GET_ALL_USERS = "select * from " + USER_TABLE;
    private static final String CREATE_USER_TABLE = "create table if not exists " + USER_TABLE
            + " (id bigint auto_increment," + " login varchar(256)," + " password varchar(256),"
            + " first_name varchar(256)," + " last_name varchar(256)," + " role_name varchar(256),"
            + " primary key (id))";
    private static final String ADD_USER = "insert into " + USER_TABLE
            + " (login, password, first_name, last_name, role_name) values (";
    private static final String DROP_USER_TABLE = "drop table " + USER_TABLE;
    private static final String DELETE_USER_BY_LOGIN = "delete from " + USER_TABLE + " where login='";

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryJdbc.class);

    private Executor executor;

    public UserRepositoryJdbc(Connection connection) {
        this.executor = new Executor(connection);
        createTable();
    }

    @Override
    public User getById(long id) {
        User resultUser = null;
        try {
            resultUser = executor.execQuery(GET_USER_BY_ID + id, result -> {
                result.next();
                User user = new User();
                user.setId(result.getLong("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setRole(result.getString("role_name"));
                return user;
            });
        } catch (SQLException e) {
            resultUser = Users.NULL_USER;
            e.printStackTrace();
        }
        return resultUser;
    }

    @Override
    public User getByLogin(String login) {
        User resultUser = null;
        try {
            resultUser = executor.execQuery(GET_USER_BY_LOGIN + login + "'", result -> {
                result.next();
                User user = new User();
                user.setId(result.getLong("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setRole(result.getString("role_name"));
                return user;
            });
        } catch (SQLException e) {
            resultUser = Users.NULL_USER;
            log.error("error while geting user by login", e);
        }
        return resultUser;
    }

    @Override
    public Collection<User> getAll() {
        List<User> resultUsers = new ArrayList<>();
        try {
            resultUsers = executor.execQuery(GET_ALL_USERS, result -> {
                List<User> users = new ArrayList<>();
                while (result.next()) {
                    User user = new User();
                    user.setId(result.getLong("id"));
                    user.setLogin(result.getString("login"));
                    user.setPassword(result.getString("password"));
                    user.setFirstName(result.getString("first_name"));
                    user.setLastName(result.getString("last_name"));
                    user.setRole(result.getString("role_name"));
                    users.add(user);
                }
                return users;
            });
        } catch (SQLException e) {
            log.info("error while getting all users", e);
        }
        return resultUsers;
    }

    @Override
    public long addUser(User user) {
        String query = ADD_USER + "'" + user.getLogin() + "', '" + user.getPassword() + "', '" + user.getFirstName()
                + "', '" + user.getLastName() + "', '" + user.getRole().toString() + "')";
        try {
            executor.execUpdate(query);
        } catch (SQLException e) {
            log.error("problems with add user", e);
        }
        user.setId(getByLogin(user.getLastName()).getId());
        return user.getId();
    }

    @Override
    public void removeByLogin(String login) {
        try {
            executor.execUpdate(DELETE_USER_BY_LOGIN + login + "'");
        } catch (SQLException e) {
            log.error("error while user remove", e);
        }
    }

    @Override
    public void createTable() {
        try {
            log.info("creating user table");
            executor.execUpdate(CREATE_USER_TABLE);
        } catch (SQLException e) {
            log.error("creation of user table is failed", e);
        }
    }

    @Override
    public void dropTable() {
        try {
            log.info("drop user table");
            executor.execUpdate(DROP_USER_TABLE);
        } catch (SQLException e) {
            log.error("droping if user table is failed", e);
        }
    }

}
