package server.issuetracker.service;

import server.issuetracker.bean.user.User;

import java.util.Collection;

public interface UserService {

    User getUserByLogin(String login);
    
    Collection<User> getAllUsers();

    void addUser(User user);

    void remove(String login);

}
