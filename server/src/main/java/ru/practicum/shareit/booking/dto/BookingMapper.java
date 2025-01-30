package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

public class BookingMapper {

    public static Booking mapToBooking(BookingDtoIn dto, Item item) {
        Booking booking = new Booking();
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());
        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item);
        booking.setBookerId(dto.getBookerId());
        return booking;
    }

    public static BookingDtoOut mapToDto(Booking booking) {
        BookingDtoOut dto = new BookingDtoOut();
        dto.setStart(booking.getStartDate());
        dto.setEnd(booking.getEndDate());
        dto.setStatus(booking.getStatus());
        dto.setId(booking.getId());
        BookerDto bookerDto = new BookerDto();
        bookerDto.setId(booking.getBookerId());
        dto.setBooker(bookerDto);
        ItemBookingDto itemBookingDto = new ItemBookingDto();
        itemBookingDto.setId(booking.getItem().getId());
        itemBookingDto.setName(booking.getItem().getName());
        dto.setItem(itemBookingDto);
        return dto;
    }
}
