package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {

    ItemRequestDto addRequest(ItemRequestDto dto);

    Collection<ItemRequestDto> getAllRequestsByUser(Integer owner);

    Collection<ItemRequestDto> getAllRequests(Integer userId);

    ItemRequestDto getRequestById(Integer requestId);
}
