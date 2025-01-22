package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

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
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Integer booker, @RequestBody ItemRequestDto dto) {
        dto.setBooker(booker);
        return bookingService.createBooking(dto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@RequestHeader("X-Sharer-User-Id") Integer owner,
                                     @PathVariable Integer bookingId,
                                     @RequestParam Boolean approved) {
        return bookingService.approveBooking(owner, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                     @PathVariable Integer bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingDto> getAllBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Integer booker,
                                                         @RequestParam(defaultValue = "all") String state) {
        return bookingService.getAllBookingsByBooker(booker, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Integer owner,
                                                        @RequestParam(defaultValue = "all") String state) {
        return bookingService.getAllBookingsByOwner(owner, state);
    }
}
