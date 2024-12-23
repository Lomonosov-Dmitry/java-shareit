package ru.practicum.shareit.item.dal;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemDal {

    public Collection<ItemDto> getAllItems();

    public Collection<ItemDto> getAllItemsByOwner(Integer ownerId);

    public ItemDto getItemById(Integer itemId);

    public ItemDto create(Item item);

    public ItemDto update(Item item);

    public void delete(Integer itemId);
}
