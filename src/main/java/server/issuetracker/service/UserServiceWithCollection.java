package server.issuetracker.service;

import server.issuetracker.bean.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserServiceWithCollection implements UserService {
    private final Map<String, User> users;

    public UserServiceWithCollection() {
        users = new HashMap<>();
    }

    @Override
    public void addUser(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public User getUserByLogin(String login) {
        return users.get(login);
    }

    @Override
    public void remove(String login) {
        users.remove(login);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
