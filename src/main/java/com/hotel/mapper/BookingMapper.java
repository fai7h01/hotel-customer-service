package com.hotel.mapper;

import com.hotel.dto.BookingDTO;
import com.hotel.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user", target = "customer")
    BookingDTO toDto(Booking entity);
    @Mapping(source = "customer", target = "user")
    Booking toEntity(BookingDTO dto);
    List<BookingDTO> toDtoList(List<Booking> list);
}
