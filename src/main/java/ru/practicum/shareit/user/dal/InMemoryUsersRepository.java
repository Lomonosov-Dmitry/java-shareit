package ru.practicum.shareit.user.dal;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;

@Repository("InMemoryUsersRepository")
public class InMemoryUsersRepository implements UserDal {
    private final HashMap<Integer, User> users;
    private int counter;

    public InMemoryUsersRepository() {
        this.users = new HashMap<>();
        counter = 0;
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUserById(Integer userId) {
        return users.get(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User createUser(User dto) {
        if (getUserByEmail(dto.getEmail()) != null)
            throw new ValidationException("Такой email уже есть!");
        User user = new User();
        user.setId(counter);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        users.put(counter, user);
        counter++;
        return user;
    }

    @Override
    public User updateUser(User user) {
        User oldUser = getUserById(user.getId());
        if (oldUser == null) {
            throw new NotFoundException("Не найдено!", "Не найден пользователь с ID = " + user.getId());
        }
        if (getUserByEmail(user.getEmail()) != null && getUserByEmail(user.getEmail()).getId() != user.getId())
            throw new ValidationException("Такой email уже есть!");
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        return oldUser;
    }

    @Override
    public void deleteUser(Integer userId) {
        users.remove(userId);
    }
}
