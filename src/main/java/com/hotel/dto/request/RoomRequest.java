package com.hotel.dto.request;

import com.hotel.enums.RoomType;

import java.time.LocalDate;

public record RoomRequest(RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
}
