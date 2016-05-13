package server.issuetracker.repository;

import java.sql.Connection;

import server.issuetracker.bean.user.User;
import server.issuetracker.db.Executor;

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
    public long getUserId(String login) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long addUser(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void createTable() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dropTable() {
        // TODO Auto-generated method stub
        
    }

}
