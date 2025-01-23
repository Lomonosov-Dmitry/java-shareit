package ru.practicum.shareit.booking.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Collection<Booking> findAllByBookerIdOrderByStartDateDesc(Integer bookerId);

    @Query(value = "select * from booking where item_id in (select id from items where owner = ?1) ORDER BY start_date DESC",
            nativeQuery = true)
    Collection<Booking> findAllByOwnerOrderByStartDate(Integer ownerId);

    @Query(value = "select * from booking where item_id in (select id from items where owner = ?1) AND status = ?2 ORDER BY start_date DESC",
            nativeQuery = true)
    Collection<Booking> findAllByOwnerAndStatusOrderByStartDate(Integer ownerId, BookingStatus status);

    Collection<Booking> findAllByBookerIdAndStatusOrderByStartDateDesc(Integer bookerId, BookingStatus status);

    Collection<Booking> findAllByBookerIdAndItemId(Integer bookerId, Integer itemId);

    Collection<Booking> findAllByItemIdAndStatusOrderByStartDateAsc(Integer itemId, BookingStatus status);
}
