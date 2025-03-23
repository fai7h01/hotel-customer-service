package com.hotel.service.impl;

import com.hotel.dto.BookingDTO;
import com.hotel.dto.UserDTO;
import com.hotel.dto.request.BookingRequest;
import com.hotel.entity.Booking;
import com.hotel.enums.BookingStatus;
import com.hotel.enums.PaymentStatus;
import com.hotel.exception.BookingNotFoundException;
import com.hotel.mapper.BookingMapper;
import com.hotel.repository.BookingRepository;
import com.hotel.service.AuthenticationService;
import com.hotel.service.BookingService;
import com.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final AuthenticationService authService;
    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, AuthenticationService authService, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.authService = authService;
        this.roomService = roomService;
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        return null;
    }

    @Override
    public BookingDTO createBooking(BookingRequest bookingRequest) {
        BookingDTO newBooking = configureBooking(bookingRequest);
        Booking savedBooking = bookingRepository.save(bookingMapper.toEntity(newBooking));
        log.info("New booking created: {}", savedBooking);
        return bookingMapper.toDto(savedBooking);
    }

    private BookingDTO configureBooking(BookingRequest bookingRequest) {
        BookingDTO booking = new BookingDTO();
        booking.setStatus(BookingStatus.PENDING);
        var foundRoom = roomService.getRoomByNumber(bookingRequest.roomNumber());
        booking.setRoom(foundRoom);
        booking.setCheckInDate(LocalDate.parse(bookingRequest.checkInDate()));
        booking.setCheckOutDate(LocalDate.parse(bookingRequest.checkOutDate()));
        booking.setTotalPrice(calculateTotalPrice(booking));
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setCustomer(authService.getLoggedInUser());
        return booking;
    }

    private BigDecimal calculateTotalPrice(BookingDTO booking) {
        var days = booking.getCheckOutDate().getDayOfYear() - booking.getCheckInDate().getDayOfYear();
        return booking.getRoom().getPricePerNight().multiply(BigDecimal.valueOf(days));
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
