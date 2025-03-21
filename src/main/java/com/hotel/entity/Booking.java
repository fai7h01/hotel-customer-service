package com.hotel.entity;

import com.hotel.enums.BookingStatus;
import com.hotel.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")
@SQLRestriction("is_deleted = false")
public class Booking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private BigDecimal totalPrice;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}