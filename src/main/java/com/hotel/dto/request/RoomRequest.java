package com.hotel.dto.request;

import java.time.LocalDate;

public record RoomRequest(LocalDate checkInDate, LocalDate checkOutDate) {
}
