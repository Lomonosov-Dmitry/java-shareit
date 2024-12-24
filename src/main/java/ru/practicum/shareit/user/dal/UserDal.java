package ru.practicum.shareit.user.dal;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserDal {

    Collection<User> getAllUsers();

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Integer userId);
}
