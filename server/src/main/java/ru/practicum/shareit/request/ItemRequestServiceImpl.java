package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dal.SqlItemsRepository;
import ru.practicum.shareit.request.dal.RequestsRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestMapper;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    @Autowired
    private final RequestsRepository requestsRepository;
    @Autowired
    private final SqlItemsRepository itemsRepository;

    @Override
    public ItemRequestDto addRequest(ItemRequestDto dto) {
        return RequestMapper.mapToDto(requestsRepository.save(RequestMapper.mapToRequest(dto)), null);
    }

    @Override
    public Collection<ItemRequestDto> getAllRequestsByUser(Integer owner) {
        return requestsRepository.findAllByUserIdOrderByCreatedDesc(owner).stream()
                .map(request -> RequestMapper.mapToDto(request, itemsRepository.findByRequestId(request.getId())
                        .stream()
                        .map(RequestMapper::mapToItemResponse)
                        .toList()))
                .toList();
    }

    @Override
    public Collection<ItemRequestDto> getAllRequests(Integer userId) {
        return requestsRepository.findAll().stream()
                .filter(request -> !Objects.equals(request.getUserId(), userId))
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
}
