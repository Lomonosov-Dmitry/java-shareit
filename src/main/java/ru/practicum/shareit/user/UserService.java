package ru.practicum.shareit.user;


import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getAllUsers();

    UserDto getUserById(Integer userId);

    UserDto getUserByEmail(String email);

    UserDto createUser(UserDto dto);

    UserDto updateUser(Integer userId, UserDto dto);

    void deleteUser(Integer userId);
}
