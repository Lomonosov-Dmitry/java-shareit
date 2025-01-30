package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dal.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dal.CommentsRepository;
import ru.practicum.shareit.item.dal.SqlItemsRepository;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.SqlUsersRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private final SqlItemsRepository itemsRepository;
    @Autowired
    private final SqlUsersRepository usersRepository;
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final CommentsRepository commentsRepository;

    @Override
    public Collection<ItemDtoComments> getAllItems() {
        Collection<ItemDtoComments> dtoList = itemsRepository.findAll().stream()
                .map(ItemMapper::mapToDtoComments)
                .toList();
        for (ItemDtoComments dto : dtoList) {
            fillBookingDates(dto);
            fillComments(dto);
        }

        return dtoList;
    }

    @Override
    public Collection<ItemDtoComments> getAllItemsByOwner(Integer ownerId) {
        Collection<ItemDtoComments> dtoList = itemsRepository.findAll().stream()
                .filter(item -> item.getOwner().equals(ownerId))
                .map(ItemMapper::mapToDtoComments)
                .toList();
        for (ItemDtoComments dto : dtoList) {
            fillBookingDates(dto);
            fillComments(dto);
        }

        return dtoList;
    }

    @Override
    public ItemDtoComments getItemById(Integer ownerId, Integer itemId) {
        checkItem(itemId);
        ItemDtoComments dto = ItemMapper.mapToDtoComments((itemsRepository.findById(itemId).orElse(null)));
        if (itemsRepository.findById(itemId).get().getOwner().equals(ownerId))
        fillBookingDates(dto);
        else {
            dto.setLastBooking(null);
            dto.setNextBooking(null);
        }
        fillComments(dto);
        return dto;
    }

    @Override
    public ItemDto create(Integer ownerId, ItemDto itemDto) {
        checkUserById(ownerId);
        return ItemMapper.mapToDto(itemsRepository.save(ItemMapper.mapToItem(itemDto, ownerId)));
    }

    @Override
    public ItemDto update(Integer ownerId, ItemDto itemDto) {
        checkUserById(ownerId);
        checkItem(itemDto.getId());
        checkItemOwner(ownerId, itemDto.getId());
        Item item = itemsRepository.findById(itemDto.getId()).get();
        if (itemDto.getName() != null)
            item.setName(itemDto.getName());
        if (itemDto.getDescription() != null)
            item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null)
            item.setAvailable(itemDto.getAvailable());
        return ItemMapper.mapToDto(itemsRepository.save(item));
    }

    @Override
    public void delete(Integer itemId) {
        itemsRepository.deleteById(itemId);
    }

    @Override
    public Collection<ItemDto> searchForItem(String text) {
        Collection<ItemDto> result = new LinkedList<>();
        if (!text.isBlank())
            result = itemsRepository.findAll().stream()
                    .filter(Item::getAvailable)
                    .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                            item.getDescription().toLowerCase().contains(text.toLowerCase()))
                    .map(ItemMapper::mapToDto)
                    .toList();
        return result;
    }

    @Override
    public CommentDto addComment(Integer userId, Integer itemId, RequestForComment text) {
        checkItem(itemId);
        checkUserById(userId);
        Collection<Booking> bookings = bookingRepository.findAllByBookerIdAndItemId(userId, itemId);
        if (bookings.isEmpty())
            throw new ValidationException("Не арендатор!", "Вы не брали эту вещь в аренду!");
        bookings = bookings.stream().sorted(Comparator.comparing(Booking::getEndDate)).toList();
        if (bookings.stream().findFirst().get().getEndDate().isAfter(LocalDateTime.now()))
            throw new ValidationException("Бронирование не завершено!", "Комментировать можно только после завершения бронирования!");
        Comment comment = new Comment();
        comment.setComment(text.getText());
        comment.setItemId(itemId);
        comment.setUserId(userId);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.mapToDto(commentsRepository.save(comment), usersRepository.findById(userId).get().getName());
    }

    private void checkUserById(Integer userId) {
        if (usersRepository.findById(userId).isEmpty())
            throw new NotFoundException("Пользователь не найден!", "Не найден пользователь с ID = " + userId);
    }

    private void checkItemOwner(Integer ownerId, Integer itemId) {
        if (!itemsRepository.findById(itemId).get().getOwner().equals(ownerId))
            throw new ValidationException("Не владелец!", "Пользователь с ID = " + ownerId + " не владелец предмета с ID = " + itemId);
    }

    private void checkItem(Integer itemId) {
        if (itemsRepository.findById(itemId).isEmpty())
            throw new NotFoundException("Предмет не найден!", "Не найден предмет с ID = " + itemId);
    }

    private void fillBookingDates(ItemDtoComments dto) {
        LocalDateTime lastBooking = null;
        LocalDateTime nextBooking = null;

        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings = bookingRepository.findAllByItemIdAndStatusOrderByStartDateAsc(dto.getId(), BookingStatus.APPROVED).stream().toList();
        for (Booking booking : bookings) {
            if (booking.getEndDate().isBefore(now)) {
                lastBooking = booking.getStartDate();
            }

            if (booking.getStartDate().isAfter(now)) {
                nextBooking = booking.getStartDate();
                break;
            }
        }
        dto.setLastBooking(lastBooking);
        dto.setNextBooking(nextBooking);
    }

    private void fillComments(ItemDtoComments dto) {
        Collection<Comment> comments = commentsRepository.findAllByItemId(dto.getId()).stream().toList();
        Collection<CommentDto> dtos = new ArrayList<>();
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                CommentDto newDto = CommentMapper.mapToDto(comment, usersRepository.findById(comment.getUserId()).get().getName());
                dtos.add(newDto);
            }
        }
        dto.setComments(dtos);
    }
}
