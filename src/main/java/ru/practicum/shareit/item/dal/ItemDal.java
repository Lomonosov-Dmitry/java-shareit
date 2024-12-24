package ru.practicum.shareit.item.dal;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemDal {

    Collection<Item> getAllItems();

    Collection<Item> getAllItemsByOwner(Integer ownerId);

    Item getItemById(Integer itemId);

    Item create(Item item);

    Item update(Item item);

    void delete(Integer itemId);
}
