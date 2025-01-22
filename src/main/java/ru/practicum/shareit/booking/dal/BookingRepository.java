package ru.practicum.shareit.booking.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Collection<Booking> findAllByRequesterId(Integer requesterId);
    @Query(value = "select * from booking where item_id in (select id from items where owner = ?1)",
            nativeQuery = true)
    Collection<Booking> findAllByOwner(Integer ownerId);
    Collection<Booking> findAllByRequesterIdAndItemId(Integer requesterId, Integer itemId);
    Collection<Booking> findAllByItemIdAndStatusOrderByStartDateAsc(Integer itemId, BookingStatus status);
}
