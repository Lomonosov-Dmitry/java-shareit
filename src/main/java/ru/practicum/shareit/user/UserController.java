package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllUsers() {
        log.info("Получаем всех пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Integer userId) {
        log.info("Получаем пользователя с ID = {}", userId);
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto dto) {
        log.info("Создаем нового пользователя");
        return userService.createUser(dto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Integer userId, @RequestBody UserDto dto) {
        log.info("Обновляем пользователя с ID = {}", userId);
        return userService.updateUser(userId, dto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Удаляем пользователя с ID = {}", userId);
        userService.deleteUser(userId);
    }
}
