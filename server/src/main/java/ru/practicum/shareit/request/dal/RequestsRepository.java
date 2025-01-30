package ru.practicum.shareit.request.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

@Repository
public interface RequestsRepository extends JpaRepository<ItemRequest, Integer> {
    Collection<ItemRequest> findAllByUserIdOrderByCreatedDesc(Integer userId);
    @Query(value = "select * from requests where user_id <> ?1", nativeQuery = true)
    Collection<ItemRequest> findAllExceptUserId(Integer userId);
}
