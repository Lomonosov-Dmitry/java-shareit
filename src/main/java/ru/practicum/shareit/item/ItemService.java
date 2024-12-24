package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {

    Collection<ItemDto> getAllItems();

    Collection<ItemDto> getAllItemsByOwner(Integer ownerId);

    ItemDto getItemById(Integer itemId);

    ItemDto create(Integer ownerId, ItemDto itemDto);

    ItemDto update(Integer ownerId, ItemDto itemDto);

    void delete(Integer itemId);

    Collection<ItemDto> searchForItem(String text);
}
