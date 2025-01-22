package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {

    public static Booking mapToBooking(BookingDto dto) {
        Booking booking = new Booking();
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());
        booking.setStatus(dto.getStatus());
        booking.setItemId(dto.getItem().getId());
        booking.setRequesterId(dto.getBooker().getId());
        return booking;
    }

    public static BookingDto mapToDto(Booking booking, String itemName) {
        BookingDto dto = new BookingDto();
        dto.setStart(booking.getStartDate());
        dto.setEnd(booking.getEndDate());
        dto.setStatus(booking.getStatus());
        dto.setId(booking.getId());
        BookerDto booker = new BookerDto();
        booker.setId(booking.getRequesterId());
        dto.setBooker(booker);
        ItemBookingDto itemBookingDto = new ItemBookingDto();
        itemBookingDto.setId(booking.getItemId());
        itemBookingDto.setName(itemName);
        dto.setItem(itemBookingDto);
        return dto;
    }
}
