package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    @Autowired
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addRequest(@RequestBody ItemRequestDto dto) {
        return itemRequestService.addRequest(dto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getAllRequestsByUser(@RequestHeader("X-Sharer-User-Id") Integer owner) {
        return itemRequestService.getAllRequestsByUser(owner);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Integer requestId) {
        return itemRequestService.getRequestById(requestId);
    }
}
