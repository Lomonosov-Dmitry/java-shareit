package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dal.SqlItemsRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dal.RequestsRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponse;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dal.SqlUsersRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    @Autowired
    private final RequestsRepository requestsRepository;
    @Autowired
    private final SqlItemsRepository itemsRepository;
    @Autowired
    private final SqlUsersRepository usersRepository;

    @Override
    public ItemRequestDto addRequest(ItemRequestDto dto) {
        checkUserById(dto.getUserId());
        return RequestMapper.mapToDto(requestsRepository.save(RequestMapper.mapToRequest(dto)), null);
    }

    @Override
    public Collection<ItemRequestDto> getAllRequestsByUser(Integer owner) {
        checkUserById(owner);

        List<ItemRequest> allRequests = requestsRepository.findAllByUserIdOrderByCreatedDesc(owner).stream().toList();
        List<Integer> requests = allRequests.stream()
                .map(ItemRequest::getId)
                .toList();
        Map<Integer, List<ItemResponse>> itemsMap = new HashMap<>();
        List<Item> items = itemsRepository.findByRequests(requests).stream().toList();
        for (Integer request : requests) {
            List<ItemResponse> responses = items.stream()
                    .filter(item -> item.getRequestId().equals(request))
                    .map(RequestMapper::mapToItemResponse)
                    .toList();
            itemsMap.put(request, responses);
        }
        return allRequests.stream()
                .map(request -> RequestMapper.mapToDto(request, itemsMap.get(request.getId())))
                .toList();
    }

    @Override
    public Collection<ItemRequestDto> getAllRequests(Integer userId) {
        return requestsRepository.findAllExceptUserId(userId).stream()
                .map(request -> RequestMapper.mapToDto(request, null))
                .toList();
    }

    @Override
    public ItemRequestDto getRequestById(Integer requestId) {
        return RequestMapper.mapToDto(requestsRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Не найден!", "Не найден запрос с ID = " + requestId)), itemsRepository.findByRequestId(requestId)
                .stream()
                .map(RequestMapper::mapToItemResponse)
                .toList());
    }

    private void checkUserById(Integer userId) {
        if (usersRepository.findById(userId).isEmpty())
            throw new NotFoundException("Пользователь не найден!", "Не найден пользователь с ID = " + userId);
    }
}
