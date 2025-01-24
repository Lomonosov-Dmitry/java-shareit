package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequestDto {
    @NotNull
    private Integer itemId;
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime end;
    private Integer booker;

}
