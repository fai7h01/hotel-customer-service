package com.hotel.util;

import com.hotel.dto.BookingDTO;
import com.hotel.dto.RoomDTO;
import com.hotel.dto.request.BookingRequest;
import com.hotel.dto.request.RoomRequest;
import com.hotel.enums.RoomType;
import com.hotel.service.BookingService;
import com.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


@Slf4j
@Configuration
public class HotelServiceTools {

    private final RoomService roomService;
    private final BookingService bookingService;

    public HotelServiceTools(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @Bean
    @Description("Get current user bookings")
    public Function<Void, List<BookingDTO>> getCurrentUserBookings() {
        return getCurrentUserBookings(bookingService::getCurrentUserBookings);
    }

    @Bean
    @Description("Create booking")
    public Function<BookingRequest, BookingDTO> createBooking() {
        return createBookingFunction(bookingService::createBooking);
    }

//    @Bean
//    @Description("Get all bookings")
//    public Function<Void, List<BookingDTO>> getAllBookings() {
//        return getAllBookings(bookingService::getAllBookings);
//    }

    @Bean
    @Description("Get all available rooms by date")
    public Function<RoomRequest, List<RoomDTO>> getAvailableRoomsByDate() {
        return getAvailableRoomsByDate(roomService::getAvailableRoomsByDate);
    }

    @Bean
    @Description("Get all available rooms by room type and date")
    public Function<RoomRequest, List<RoomDTO>> getAvailableRoomsByTypeDate() {
        return getAvailableRoomsByTypeDate(roomService::getAvailableRoomsByTypeDate);
    }

//    @Bean
//    @Description("Get all available rooms")
//    public Function<Void, List<RoomDTO>> getAvailableRooms() {
//        return getAvailableRooms(roomService::getAvailableRooms);
//    }
//
//    private Function<Void, List<BookingDTO>> getAllBookings(Supplier<List<BookingDTO>> processor) {
//        return request -> {
//            List<BookingDTO> bookings = processor.get();
//            log.info("\n\n>>>>> All Bookings: {}", bookings);
//            return bookings;
//        };
//    }

    private Function<RoomRequest, List<RoomDTO>> getAvailableRoomsByDate(BiFunction<LocalDate, LocalDate, List<RoomDTO>> processor) {
        return request -> {
            List<RoomDTO> rooms = processor.apply(request.checkInDate(), request.checkOutDate());
            log.info("Available rooms for dates {} - {}: {}", request.checkInDate(), request.checkOutDate(), rooms);
            return rooms;
        };
    }

    private Function<RoomRequest, List<RoomDTO>> getAvailableRoomsByTypeDate(TriFunction<RoomType, LocalDate, LocalDate, List<RoomDTO>> processor) {
        return request -> {
            List<RoomDTO> rooms = processor.apply(request.roomType(), request.checkInDate(), request.checkOutDate());
            log.info("Available rooms for dates {} - {}: {}", request.checkInDate(), request.checkOutDate(), rooms);
            return rooms;
        };
    }

//    private Function<Void, List<RoomDTO>> getAvailableRooms(Supplier<List<RoomDTO>> roomSupplier) {
//        return request -> {
//            List<RoomDTO> rooms = roomSupplier.get();
//            log.info("\n\n>>>>> Available Rooms: {}", rooms);
//            return rooms;
//        };
//    }

    private Function<BookingRequest, BookingDTO> createBookingFunction(Function<BookingRequest, BookingDTO> processor) {
        return processor;
    }

    private Function<Void, List<BookingDTO>> getCurrentUserBookings(Supplier<List<BookingDTO>> processor) {
        return request -> {
            List<BookingDTO> bookings = processor.get();
            log.info("\n\n>>>>> All Bookings: {}", bookings);
            return bookings;
        };
    }
}
