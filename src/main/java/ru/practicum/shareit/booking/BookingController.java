package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.BookingDtoOut;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoOut createBooking(@RequestHeader("X-Sharer-User-Id") Integer bookerId, @Valid @RequestBody BookingDtoIn dto) {
        dto.setBookerId(bookerId);
        return bookingService.createBooking(dto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoOut approveBooking(@RequestHeader("X-Sharer-User-Id") Integer owner,
                                        @PathVariable Integer bookingId,
                                        @RequestParam Boolean approved) {
        return bookingService.approveBooking(owner, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoOut getBookingById(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                        @PathVariable Integer bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingDtoOut> getAllBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Integer booker,
                                                            @RequestParam(defaultValue = "all") String state) {
        return bookingService.getAllBookingsByBooker(booker, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDtoOut> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Integer owner,
                                                           @RequestParam(defaultValue = "all") String state) {
        return bookingService.getAllBookingsByOwner(owner, state);
    }
}
