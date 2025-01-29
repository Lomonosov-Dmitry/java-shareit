package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;


/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequestDto {
    private Integer id;
    @NotNull
    private String description;
    private LocalDateTime created;
    private Integer userId;
    private Collection<ItemResponse> items;
}
