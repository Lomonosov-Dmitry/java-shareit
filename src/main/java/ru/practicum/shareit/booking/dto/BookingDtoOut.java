package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDtoOut {
    private Integer id;
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime end;
    private BookingStatus status;
    private BookerDto booker;
    @NotNull
    private ItemBookingDto item;
}
