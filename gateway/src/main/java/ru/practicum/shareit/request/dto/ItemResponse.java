package ru.practicum.shareit.request.dto;

import lombok.Data;

@Data
public class ItemResponse {
    private Integer itemId;
    private String name;
    private Integer ownerId;
}
