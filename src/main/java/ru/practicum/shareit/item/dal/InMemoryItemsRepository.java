package ru.practicum.shareit.item.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.UserDal;

import java.util.Collection;
import java.util.HashMap;

@Repository("InMemoryItemsRepository")
public class InMemoryItemsRepository implements ItemDal {
    private final HashMap<Integer, Item> items;
    private final UserDal users;
    private int counter;

    public InMemoryItemsRepository(@Autowired UserDal users) {
        this.items = new HashMap<>();
        this.users = users;
        this.counter = 0;
    }

    @Override
    public Collection<ItemDto> getAllItems() {
        return items.values().stream()
                .map(ItemMapper::mapToDto)
                .toList();
    }

    @Override
    public Collection<ItemDto> getAllItemsByOwner(Integer ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().equals(ownerId))
                .map(ItemMapper::mapToDto)
                .toList();
    }

    @Override
    public ItemDto getItemById(Integer itemId) {
        if (items.containsKey(itemId))
            return ItemMapper.mapToDto(items.get(itemId));
        else {
            return null;
        }
    }

    @Override
    public ItemDto create(Item item) {
        if (users.getUserById(item.getOwner()) == null)
            throw new NotFoundException("Владелец не найден!", "Не найден владелец с ID = " + item.getOwner());
        item.setId(counter);
        items.put(counter, item);
        counter++;
        return ItemMapper.mapToDto(item);
    }

    @Override
    public ItemDto update(Item item) {
        if (users.getUserById(item.getOwner()) == null)
            throw new NotFoundException("Владелец не найден!", "Не найден владелец с ID = " + item.getOwner());
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
        return ItemMapper.mapToDto(oldItem);
    }

    @Override
    public void delete(Integer itemId) {
        items.remove(itemId);
    }
}
