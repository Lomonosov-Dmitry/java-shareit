package ru.practicum.shareit.item.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Repository
public interface SqlItemsRepository extends JpaRepository<Item, Integer> {

    Collection<Item> findByOwner(Integer owner);

    Collection<Item> findByRequestId(Integer requestId);
}
