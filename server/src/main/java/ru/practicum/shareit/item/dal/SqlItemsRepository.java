package ru.practicum.shareit.item.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

@Repository
public interface SqlItemsRepository extends JpaRepository<Item, Integer> {

    Collection<Item> findByOwner(Integer owner);

    Collection<Item> findByRequestId(Integer requestId);

    @Query(value = "select * from items where request_id in (?1)", nativeQuery = true)
    Collection<Item> findByRequests(List<Integer> requests);
}
