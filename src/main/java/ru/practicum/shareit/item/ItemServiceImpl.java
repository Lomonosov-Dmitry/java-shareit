package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dal.ItemDal;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;

import java.util.Collection;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Autowired
    private final ItemDal itemsRepository;

    @Override
    public Collection<ItemDto> getAllItemsByOwner(Integer ownerId) {
        return itemsRepository.getAllItemsByOwner(ownerId);
    }

    @Override
    public Collection<ItemDto> getAllItems() {
        return itemsRepository.getAllItems();
    }

    @Override
    public ItemDto getItemById(Integer itemId) {
        return itemsRepository.getItemById(itemId);
    }

    @Override
    public ItemDto create(Integer ownerId, ItemDto itemDto) {
        return itemsRepository.create(ItemMapper.mapToItem(itemDto, ownerId));
    }

    @Override
    public ItemDto update(Integer ownerId, ItemDto itemDto) {
        return itemsRepository.update(ItemMapper.mapToItem(itemDto, ownerId));
    }

    @Override
    public void delete(Integer itemId) {
        itemsRepository.delete(itemId);
    }

    @Override
    public Collection<ItemDto> searchForItem(String text) {
        Collection<ItemDto> result = new LinkedList<ItemDto>();
        if (!text.isBlank())
            result = itemsRepository.getAllItems().stream()
                    .filter(itemDto -> itemDto.getName().toLowerCase().contains(text.toLowerCase()) ||
                            itemDto.getDescription().toLowerCase().contains(text.toLowerCase()))
                    .filter(ItemDto::getAvailable)
                    .toList();
        return result;
    }
}
