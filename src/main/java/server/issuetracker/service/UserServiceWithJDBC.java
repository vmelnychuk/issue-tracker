package server.issuetracker.service;

import server.issuetracker.bean.user.User;
import server.issuetracker.repository.UserRepository;

import java.util.Collection;

public class UserServiceWithJDBC implements UserService {

    private final UserRepository userRepository;

    public UserServiceWithJDBC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User getUserByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public void remove(String login) {
        userRepository.removeByLogin(login);
    }
}
