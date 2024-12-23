package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserDao;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao usersRepository;

    public UserServiceImpl(@Autowired UserDao usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Collection<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    public User getUserById(Integer userId) {
        return usersRepository.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return usersRepository.getUserByEmail(email);
    }

    @Override
    public User createUser(User user) {
        return usersRepository.createUser(user);
    }

    @Override
    public User updateUser(Integer userId, User user) {
        user.setId(userId);
        return usersRepository.updateUser(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        usersRepository.deleteUser(userId);
    }
}
