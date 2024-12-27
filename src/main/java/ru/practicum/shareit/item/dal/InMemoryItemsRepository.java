package ru.practicum.shareit.item.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.UserDal;

import java.util.Collection;
import java.util.HashMap;

@Repository("InMemoryItemsRepository")
public class InMemoryItemsRepository implements ItemDal {
    private final HashMap<Integer, Item> items;
    private int counter;

    public InMemoryItemsRepository(@Autowired UserDal users) {
        this.items = new HashMap<>();
        this.counter = 0;
    }

    @Override
    public Collection<Item> getAllItems() {
        return items.values();
    }

    @Override
    public Collection<Item> getAllItemsByOwner(Integer ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().equals(ownerId))
                .toList();
    }

    @Override
    public Item getItemById(Integer itemId) {
        return items.getOrDefault(itemId, null);
    }

    @Override
    public Item create(Item item) {
        item.setId(counter);
        items.put(counter, item);
        counter++;
        return item;
    }

    @Override
    public Item update(Item item) {
        if (!items.containsKey(item.getId()))
            throw new NotFoundException("Вещь не найдена!", "Не найдена вещь с ID = " + item.getId());
        Item oldItem = items.get(item.getId());
        if (!oldItem.getOwner().equals(item.getOwner()))
            throw new ValidationException("Не владелец!", "Вы не являетесь владельцем этой вещи!");
        if (item.getName() != null)
            oldItem.setName(item.getName());
        if (item.getDescription() != null)
            oldItem.setDescription(item.getDescription());
        if (item.getAvailable() != null)
            oldItem.setAvailable(item.getAvailable());
        return oldItem;
    }

    @Override
    public void delete(Integer itemId) {
        items.remove(itemId);
    }
}
