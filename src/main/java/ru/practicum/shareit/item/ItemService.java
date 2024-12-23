package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {

    public Collection<ItemDto> getAllItems();

    public Collection<ItemDto> getAllItemsByOwner(Integer ownerId);

    public ItemDto getItemById(Integer itemId);

    public ItemDto create(Integer ownerId, ItemDto itemDto);

    public ItemDto update(Integer ownerId, ItemDto itemDto);

    public void delete(Integer itemId);

    public Collection<ItemDto> searchForItem(String text);
}
