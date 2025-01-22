package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.booking.model.Booking;

public class RequestMapper {

    public static Booking mapToBooking(ItemRequestDto requestDto) {
        Booking booking = new Booking();
        booking.setRequesterId(requestDto.getBooker());
        booking.setItemId(requestDto.getItemId());
        booking.setStartDate(requestDto.getStart());
        booking.setEndDate(requestDto.getEnd());
        return booking;
    }
}
