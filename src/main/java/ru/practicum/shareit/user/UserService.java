package ru.practicum.shareit.user;


import java.util.Collection;

public interface UserService {

    public Collection<User> getAllUsers();

    public User getUserById(Integer userId);

    public User getUserByEmail(String email);

    public User createUser(User user);

    public User updateUser(Integer userId, User user);

    public void deleteUser(Integer userId);
}
