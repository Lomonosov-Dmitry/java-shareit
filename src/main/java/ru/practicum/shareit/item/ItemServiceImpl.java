package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dal.ItemDal;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.UserDal;

import java.util.Collection;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Autowired
    private final ItemDal itemsRepository;
    @Autowired
    private final UserDal users;

    @Override
    public Collection<ItemDto> getAllItemsByOwner(Integer ownerId) {
        checkUserById(ownerId);
        return itemsRepository.getAllItemsByOwner(ownerId).stream()
                .map(ItemMapper::mapToDto)
                .toList();
    }

    @Override
    public Collection<ItemDto> getAllItems() {
        return itemsRepository.getAllItems().stream()
                .map(ItemMapper::mapToDto)
                .toList();
    }

    @Override
    public ItemDto getItemById(Integer itemId) {
        return ItemMapper.mapToDto(itemsRepository.getItemById(itemId));
    }

    @Override
    public ItemDto create(Integer ownerId, ItemDto itemDto) {
        checkUserById(ownerId);
        return ItemMapper.mapToDto(itemsRepository.create(ItemMapper.mapToItem(itemDto, ownerId)));
    }

    @Override
    public ItemDto update(Integer ownerId, ItemDto itemDto) {
        checkUserById(ownerId);
        return ItemMapper.mapToDto(itemsRepository.update(ItemMapper.mapToItem(itemDto, ownerId)));
    }

    @Override
    public void delete(Integer itemId) {
        itemsRepository.delete(itemId);
    }

    @Override
    public Collection<ItemDto> searchForItem(String text) {
        Collection<ItemDto> result = new LinkedList<>();
        if (!text.isBlank())
            result = itemsRepository.getAllItems().stream()
                    .filter(Item::getAvailable)
                    .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                            item.getDescription().toLowerCase().contains(text.toLowerCase()))
                    .map(ItemMapper::mapToDto)
                    .toList();
        return result;
    }

    private void checkUserById(Integer userId) {
        if (users.getUserById(userId) == null)
            throw new NotFoundException("Пользователь не найден!", "Не найден пользователь с ID = " + userId);
    }
}
