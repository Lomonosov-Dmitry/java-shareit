package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDtoIn {
    private Integer id;
    @NotNull
    @FutureOrPresent
    private LocalDateTime start;
    @NotNull
    @Future
    private LocalDateTime end;
    @NotNull
    private Integer itemId;
    private Integer bookerId;
}
