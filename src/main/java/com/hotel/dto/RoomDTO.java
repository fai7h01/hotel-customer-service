package com.hotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private BigDecimal pricePerNight;
    private int capacity;
    private int floorNumber;
    private boolean isAvailable;
}
