package com.hotel.entity;

import com.hotel.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rooms")
@SQLRestriction("is_deleted = false")
public class Room extends BaseEntity {

    @Column(unique = true)
    private String roomNumber;
    
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    
    private BigDecimal pricePerNight;
    private int capacity;
    private int floorNumber;
    @OneToMany(mappedBy = "room")
    private Set<Booking> bookings;
}