package server.issuetracker.utils;

import server.issuetracker.bean.user.Role;
import server.issuetracker.bean.user.User;

public class Users {
    public static final User NULL_USER = new User();
    
    public static User createUser(String login, String password, String firstName, String lastName) {
        User user = new User();
        user.setEmail(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(Role.USER);
        return user;
    }

    public static User createAdmin(String login, String password, String firstName, String lastName) {
        User user = new User();
        user.setEmail(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(Role.ADMINISTRATOR);
        return user;
    }
    
    public static User createNullUser() {
        return NULL_USER;
    }
}
