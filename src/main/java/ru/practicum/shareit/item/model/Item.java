package ru.practicum.shareit.item.model;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    Integer id;
    String name;
    String description;
    Boolean available;
    Integer owner;
    Integer leaseCount;
}
