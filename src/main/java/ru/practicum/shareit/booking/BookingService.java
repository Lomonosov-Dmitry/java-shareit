package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dal.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dal.SqlItemsRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.user.dal.SqlUsersRepository;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BookingService {
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final SqlItemsRepository itemsRepository;
    @Autowired
    private final SqlUsersRepository usersRepository;

    public BookingDto createBooking(ItemRequestDto dto) {
        checkUser(dto.getBooker());
        if (itemsRepository.findById(dto.getItemId()).isEmpty())
            throw new NotFoundException("Предмет не найден!", "Не найден предмет с ID = " + dto.getItemId());
        if (dto.getStart().isAfter(dto.getEnd()))
            throw new ValidationException("Неверные сроки аренды!", "Дата начала не может быть позже даты завершения!");
        if (dto.getStart().isBefore(ChronoLocalDateTime.from(LocalDateTime.now())))
            throw new ValidationException("Неверные сроки аренды!", "Дата начала аренды не может в прошлом!");
        if (dto.getEnd().isBefore(ChronoLocalDateTime.from(LocalDateTime.now())))
            throw new ValidationException("Неверные сроки аренды!", "Дата завершения аренды не может в прошлом!");
        if (dto.getStart().isEqual(dto.getEnd()))
            throw new ValidationException("Неверные сроки аренды!", "Даты начала и завершения аренды не могут совпадать!");
        if (!itemsRepository.findById(dto.getItemId()).get().getAvailable())
            throw new ValidationException("Недоступно!", "Предмет не доступен для аренды!");

        Booking booking = RequestMapper.mapToBooking(dto);
        booking.setStatus(BookingStatus.WAITING);
        return BookingMapper.mapToDto(bookingRepository.save(booking), itemsRepository.findById(booking.getItemId()).get().getName());
    }

    public BookingDto approveBooking(Integer owner, Integer bookingId, Boolean approve) {
        checkBooking(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();
        if (!itemsRepository.findById(booking.getItemId()).get().getOwner().equals(owner))
            throw new ValidationException("Не владелец!", "Подтверждать бронирование может только владелец!");
        if (approve)
            booking.setStatus(BookingStatus.APPROVED);
        else
            booking.setStatus(BookingStatus.REJECTED);
        return BookingMapper.mapToDto(bookingRepository.save(booking), itemsRepository.findById(booking.getItemId()).get().getName());
    }

    public BookingDto getBookingById(Integer userId, Integer bookingId) {
        checkBooking(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();
        if (!booking.getRequesterId().equals(userId) && !itemsRepository.findById(booking.getItemId()).get().getOwner().equals(userId))
            throw new ValidationException("Не владелец, не арендатор!", "Получать информацию по бронированию может либо владелец, либо арендатор");
        return BookingMapper.mapToDto(booking, itemsRepository.findById(booking.getItemId()).get().getName());
    }

    public Collection<BookingDto> getAllBookingsByBooker(Integer booker, String state) {
        checkUser(booker);
        return sortByState(bookingRepository.findAllByRequesterId(booker), state);
    }

    public Collection<BookingDto> getAllBookingsByOwner(Integer owner, String state) {
        checkUser(owner);
        Collection<BookingDto> bookingsDto = new ArrayList<>();
        Collection<Item> items = itemsRepository.findByOwner(owner);
        if (!items.isEmpty()) {
            bookingsDto = sortByState(bookingRepository.findAllByOwner(owner), state);
        }
        return bookingsDto;
    }

    private void checkBooking(Integer bookingId) {
        if (bookingRepository.findById(bookingId).isEmpty())
            throw new NotFoundException("Не найдено!", "Не найдено бронирование с ID = " + bookingId);
    }

    private void checkUser(Integer userId) {
        if (usersRepository.findById(userId).isEmpty())
            throw new NotFoundException("Пользователь не найден!", "Не найден пользователь с ID = " + userId);
    }

    private Collection<BookingDto> sortByState(Collection<Booking> bookings, String state) {
        if (bookings.isEmpty())
            return new ArrayList<>();
        switch (state) {
            case "current": {
                bookings = bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                        .filter(booking -> booking.getStartDate().isBefore(LocalDateTime.now()))
                        .filter(booking -> booking.getEndDate().isAfter(LocalDateTime.now()))
                        .toList();
                break;
            }
            case "past": {
                bookings = bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                        .filter(booking -> booking.getEndDate().isBefore(LocalDateTime.now()))
                        .toList();
                break;
            }
            case "future": {
                bookings = bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                        .filter(booking -> booking.getStartDate().isAfter(LocalDateTime.now()))
                        .toList();
                break;
            }
            case "waiting": {
                bookings = bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.WAITING))
                        .toList();
                break;
            }
            case "rejected": {
                bookings = bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.REJECTED))
                        .toList();
                break;
            }
        }
        bookings = bookings.stream()
                .sorted((booking1, booking2) -> booking1.getStartDate().compareTo(booking2.getStartDate()))
                .toList();
        return bookings.stream()
                .map(booking -> BookingMapper.mapToDto(booking, itemsRepository.findById(booking.getItemId()).get().getName()))
                .toList();
    }
}
