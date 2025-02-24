package ru.practicum.shareit.user.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

@Repository
public interface SqlUsersRepository extends JpaRepository<User, Integer> {
}
