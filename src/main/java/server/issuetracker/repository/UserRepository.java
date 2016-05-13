package server.issuetracker.repository;

import server.issuetracker.bean.user.User;

public interface UserRepository {
    public User get(long id);
    public long getUserId(String login);
    public long addUser(User user);
    public void createTable();
    public void dropTable();
}
