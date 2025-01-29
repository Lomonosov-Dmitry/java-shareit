package ru.practicum.shareit.item.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemResponse;

import java.util.Collection;

@Repository
public interface SqlItemsRepository extends JpaRepository<Item, Integer> {
    Collection<Item> findByOwner(Integer owner);
    //@Query(value = "select id, name, owner from items where request_id = ?1", nativeQuery = true)
    Collection<Item> findByRequestId(Integer requestId);
}
