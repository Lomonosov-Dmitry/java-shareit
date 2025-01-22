package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dal.SqlUsersRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final SqlUsersRepository usersRepository;

    @Override
    public Collection<UserDto> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return UserMapper.mapUserToDto(Objects.requireNonNull(usersRepository.findById(userId).orElse(null)));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDto createUser(UserDto dto) {
        return UserMapper.mapUserToDto(usersRepository.save(UserMapper.mapDtoToUser(dto)));
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto dto) {
        if (usersRepository.findById(userId).isPresent()) {
            User user = usersRepository.findById(userId).get();
            if (dto.getName() != null)
                user.setName(dto.getName());
            if (dto.getEmail() != null)
                user.setEmail(dto.getEmail());
            return UserMapper.mapUserToDto(usersRepository.save(user));
        }
        else
            throw new NotFoundException("Пользователь не найден!", "Не найден пользователь с ID = " + userId);
    }

    @Override
    public void deleteUser(Integer userId) {
        usersRepository.deleteById(userId);
    }
}
