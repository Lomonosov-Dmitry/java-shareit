package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dal.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoOut;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dal.SqlItemsRepository;
import ru.practicum.shareit.user.dal.SqlUsersRepository;

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

    public BookingDtoOut createBooking(BookingDtoIn dto) {
        checkUser(dto.getBookerId());
        if (itemsRepository.findById(dto.getItemId()).isEmpty())
            throw new NotFoundException("Предмет не найден!", "Не найден предмет с ID = " + dto.getItemId());
        if (!itemsRepository.findById(dto.getItemId()).get().getAvailable())
            throw new ValidationException("Недоступно!", "Предмет не доступен для аренды!");

        Booking booking = BookingMapper.mapToBooking(dto, itemsRepository.findById(dto.getItemId()).get());
        return BookingMapper.mapToDto(bookingRepository.save(booking));
    }

    public BookingDtoOut approveBooking(Integer owner, Integer bookingId, Boolean approve) {
        Booking booking = checkBooking(bookingId);
        if (!booking.getItem().getOwner().equals(owner))
            throw new ValidationException("Не владелец!", "Подтверждать бронирование может только владелец!");
        if (!booking.getStatus().equals(BookingStatus.WAITING))
            throw new ValidationException("Не ждет подтверждения!", "Бронирование не ожидает подтверждения");
        if (approve)
            booking.setStatus(BookingStatus.APPROVED);
        else
            booking.setStatus(BookingStatus.REJECTED);
        return BookingMapper.mapToDto(bookingRepository.save(booking));
    }

    public BookingDtoOut getBookingById(Integer userId, Integer bookingId) {
        Booking booking = checkBooking(bookingId);
        if (!booking.getBookerId().equals(userId) && !booking.getItem().getOwner().equals(userId))
            throw new ValidationException("Не владелец, не арендатор!", "Получать информацию по бронированию может либо владелец, либо арендатор");
        return BookingMapper.mapToDto(booking);
    }

    public Collection<BookingDtoOut> getAllBookingsByBooker(Integer booker, String state) {
        checkUser(booker);
        if (state.equalsIgnoreCase("all"))
            return bookingRepository.findAllByBookerIdOrderByStartDateDesc(booker).stream()
                    .map(BookingMapper::mapToDto)
                    .toList();
        else
            return bookingRepository.findAllByBookerIdAndStatusOrderByStartDateDesc(booker, BookingStatus.valueOf(state)).stream()
                    .map(BookingMapper::mapToDto)
                    .toList();
    }

    public Collection<BookingDtoOut> getAllBookingsByOwner(Integer owner, String state) {
        checkUser(owner);
        if (!itemsRepository.findByOwner(owner).isEmpty() && state.equals("all"))
            return bookingRepository.findAllByOwnerOrderByStartDate(owner).stream()
                    .map(BookingMapper::mapToDto)
                    .toList();
        else
            return bookingRepository.findAllByOwnerAndStatusOrderByStartDate(owner, BookingStatus.valueOf(state)).stream()
                    .map(BookingMapper::mapToDto)
                    .toList();
    }

    private Booking checkBooking(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Не найдено!", "Не найдено бронирование с ID = " + bookingId));
    }

    private void checkUser(Integer userId) {
        if (usersRepository.findById(userId).isEmpty())
            throw new NotFoundException("Пользователь не найден!", "Не найден пользователь с ID = " + userId);
    }
}
