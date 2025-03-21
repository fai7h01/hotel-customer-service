package com.hotel.service;

import com.hotel.dto.BookingDTO;
import com.hotel.enums.BookingStatus;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getBookingById(Long id);
    List<BookingDTO> getAllBookings();

    List<BookingDTO> getBookingsByUserId(Long userId);
    List<BookingDTO> getCurrentUserBookings();
    List<BookingDTO> getBookingsByRoomId(Long roomId);

    BookingDTO updateBookingStatus(Long id, BookingStatus status);
}
