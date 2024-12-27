package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dal.UserDal;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private final UserDal usersRepository;

    public UserServiceImpl(@Autowired UserDal usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return usersRepository.getAllUsers().stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return UserMapper.mapUserToDto(usersRepository.getUserById(userId));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return UserMapper.mapUserToDto(usersRepository.getUserByEmail(email));
    }

    @Override
    public UserDto createUser(UserDto dto) {
        return UserMapper.mapUserToDto(usersRepository.createUser(UserMapper.mapDtoToUser(dto)));
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto dto) {
        dto.setId(userId);
        return UserMapper.mapUserToDto(usersRepository.updateUser(UserMapper.mapDtoToUser(dto)));
    }

    @Override
    public void deleteUser(Integer userId) {
        usersRepository.deleteUser(userId);
    }
}
