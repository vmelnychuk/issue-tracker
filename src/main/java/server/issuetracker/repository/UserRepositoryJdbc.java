package server.issuetracker.repository;

import server.issuetracker.bean.user.User;
import server.issuetracker.db.Executor;

import java.sql.Connection;
import java.util.Collection;

public class UserRepositoryJdbc implements UserRepository {
    private static final String USER_TABLE = "users";
    private static final String GET_USER = "select * from ? where user_name='?'";
    
    private Executor executor;
    
    public UserRepositoryJdbc(Connection connection) {
        this.executor = new Executor(connection);
    }
    
    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public User getByLogin(String login) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public long addUser(User user) {

        return 0;
    }

    @Override
    public void remove(String login) {

    }

    @Override
    public void createTable() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dropTable() {
        // TODO Auto-generated method stub
        
    }

    private void cleanUp() {
        dropTable();
    }

    private void setUp() {
        createTable();
    }

}
