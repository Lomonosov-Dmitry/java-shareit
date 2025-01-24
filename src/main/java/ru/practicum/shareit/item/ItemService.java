package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoComments;
import ru.practicum.shareit.item.dto.RequestForComment;

import java.util.Collection;

public interface ItemService {

    Collection<ItemDtoComments> getAllItems();

    Collection<ItemDtoComments> getAllItemsByOwner(Integer ownerId);

    ItemDtoComments getItemById(Integer ownerId, Integer itemId);

    ItemDto create(Integer ownerId, ItemDto itemDto);

    ItemDto update(Integer ownerId, ItemDto itemDto);

    void delete(Integer itemId);

    Collection<ItemDto> searchForItem(String text);

    CommentDto addComment(Integer userId, Integer itemId, RequestForComment text);
}
