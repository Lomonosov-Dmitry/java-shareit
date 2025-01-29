package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static ItemDto mapToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        return dto;
    }

    public static Item mapToItem(ItemDto dto, Integer owner) {
        Item item = new Item();
        item.setOwner(owner);
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setAvailable(dto.getAvailable());
        item.setRequestId(dto.getRequestId());
        return item;
    }

    public static ItemDtoComments mapToDtoComments(Item item) {
        ItemDtoComments dto = new ItemDtoComments();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        return dto;
    }
}
