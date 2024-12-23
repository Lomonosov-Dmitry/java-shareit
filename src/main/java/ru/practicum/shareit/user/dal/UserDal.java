package ru.practicum.shareit.user.dal;

import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserDal {

    public Collection<User> getAllUsers();

    public User getUserById(Integer userId);

    public User getUserByEmail(String email);

    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(Integer userId);
}
