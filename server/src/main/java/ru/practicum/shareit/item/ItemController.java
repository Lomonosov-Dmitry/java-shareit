package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoComments;
import ru.practicum.shareit.item.dto.RequestForComment;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDtoComments> getAllItemsById(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return itemService.getAllItemsByOwner(ownerId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoComments getItemById(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @PathVariable Integer itemId) {
        return itemService.getItemById(ownerId, itemId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @RequestBody ItemDto itemDto) {
        return itemService.create(ownerId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                          @PathVariable Integer itemId,
                          @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        return itemService.update(ownerId, itemDto);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDto> searchForItem(@RequestParam String text) {
        return itemService.searchForItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto newComment(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                 @PathVariable Integer itemId,
                                 @RequestBody RequestForComment text) {
      return itemService.addComment(userId, itemId, text);
    }


}
