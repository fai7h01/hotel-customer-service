package com.hotel.util;

import com.hotel.dto.RoomDTO;
import com.hotel.service.BookingService;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class HotelServiceTools {

    private final RoomService roomService;
    private final BookingService bookingService;


    @Bean
    @Description("Get all available rooms")
    public Function<Void, List<RoomDTO>> getAvailableRooms() {
        return getAvailableRooms(roomService::getAvailableRooms);
    }


    private Function<Void, List<RoomDTO>> getAvailableRooms(Supplier<List<RoomDTO>> roomSupplier) {
        return request -> {
            List<RoomDTO> rooms = roomSupplier.get();
            log.info("\n\n>>>>> Available Rooms: {}", rooms);
            return rooms;
        };
    }
}
