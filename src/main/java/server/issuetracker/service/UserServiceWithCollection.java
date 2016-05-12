package server.issuetracker.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import server.issuetracker.bean.user.User;

public class UserServiceWithCollection implements UserService {
    private final Map<String, User> users;
    private final Map<String, User> loggedUsers;

    public UserServiceWithCollection() {
        users = new HashMap<>();
        loggedUsers = new HashMap<>();
    }

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public User getUserByLogin(String login) {
        return users.get(login);
    }

    @Override
    public User getUserBySessionId(String sessionId) {
        return loggedUsers.get(sessionId);
    }

    @Override
    public void logIn(String sessionId, User user) {
        loggedUsers.put(sessionId, user);
    }

    @Override
    public void logOut(String sessionId) {
        loggedUsers.remove(sessionId);
    }

    @Override
    public void remove(String login) {
        loggedUsers.remove(login);
        users.remove(login);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
