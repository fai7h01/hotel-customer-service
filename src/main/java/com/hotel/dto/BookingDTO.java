package com.hotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.enums.BookingStatus;
import com.hotel.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class BookingDTO {

    private Long id;
    private UserDTO customer;
    private RoomDTO room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
}
