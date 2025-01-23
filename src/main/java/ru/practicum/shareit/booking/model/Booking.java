package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "booker_id")
    private Integer bookerId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
