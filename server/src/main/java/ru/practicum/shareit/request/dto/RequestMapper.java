package ru.practicum.shareit.request.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class RequestMapper {

    public static ItemRequest mapToRequest(ItemRequestDto dto) {
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDateTime.now());
        request.setDescription(dto.getDescription());
        request.setUserId(dto.getUserId());
        return request;
    }

    public static ItemRequestDto mapToDto(ItemRequest request, Collection<ItemResponse> responses) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());
        dto.setCreated(request.getCreated());
        dto.setUserId(request.getUserId());
        dto.setItems(responses);
        return dto;
    }

    public static ItemResponse mapToItemResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setItemId(item.getId());
        response.setName(item.getName());
        response.setOwnerId(item.getOwner());
        return response;
    }
}
