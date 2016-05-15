package server.issuetracker.service;

import server.issuetracker.bean.user.User;

public interface LoginService {
    User getUserBySessionId(String sessionId);
    void logIn(String sessionId, User user);
    void logOut(String sessionId);
    User checkLogin(String login, String password);
}
