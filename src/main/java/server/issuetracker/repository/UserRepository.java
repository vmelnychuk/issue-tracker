package server.issuetracker.repository;

import server.issuetracker.bean.user.User;

import java.util.Collection;

public interface UserRepository {
    public User getById(long id);
    public User getByLogin(String login);
    public Collection<User> getAll();
    public long addUser(User user);
    public void removeByLogin(String login);

    public void createTable();
    public void dropTable();
}
