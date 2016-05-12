package server.issuetracker.service;

import java.util.Collection;

import server.issuetracker.bean.user.User;

public interface UserService {

    User getUserByLogin(String login);
    
    Collection<User> getAllUsers();

    void addUser(User user);

    User getUserBySessionId(String sessionId);

    void logIn(String sessionId, User user);

    void logOut(String sessionId);
    
    void remove(String login);

}
