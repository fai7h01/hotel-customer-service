package com.hotel.service.impl;

import com.hotel.dto.BookingDTO;
import com.hotel.enums.BookingStatus;
import com.hotel.exception.BookingNotFoundException;
import com.hotel.mapper.BookingMapper;
import com.hotel.repository.BookingRepository;
import com.hotel.service.AuthenticationService;
import com.hotel.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final AuthenticationService authService;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, AuthenticationService authService) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.authService = authService;
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        return null;
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingMapper.toDtoList(bookingRepository.findAll());
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        return bookingMapper.toDtoList(bookingRepository.findAllByUserId(userId));
    }

    @Override
    public List<BookingDTO> getCurrentUserBookings() {
        return bookingMapper.toDtoList(bookingRepository.findAllByUserId(authService.getLoggedInUser().getId()));
    }

    @Override
    public List<BookingDTO> getBookingsByRoomId(Long roomId) {
        return bookingMapper.toDtoList(bookingRepository.findAllByRoomId(roomId));
    }

    @Override
    public BookingDTO updateBookingStatus(Long id, BookingStatus status) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
        booking.setStatus(status);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
