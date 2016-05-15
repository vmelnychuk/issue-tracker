package server.issuetracker.service;

import server.issuetracker.bean.user.User;
import server.issuetracker.utils.Users;

import java.util.HashMap;
import java.util.Map;

public class LoginServiceWithCollection implements LoginService {
    private final Map<String, User> loggedUsers;
    private final UserService userService;

    public LoginServiceWithCollection(UserService userService) {
        this.userService = userService;
        this.loggedUsers = new HashMap<>();
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
    public User checkLogin(String login, String password) {
        User user = userService.getUserByLogin(login);
        if (user != null || user.getPassword().equals(password)) {
            return user;
        } else {
            return Users.NULL_USER;
        }
    }
}
