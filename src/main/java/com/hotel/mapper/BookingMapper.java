package com.hotel.mapper;

import com.hotel.dto.BookingDTO;
import com.hotel.entity.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDTO toDto(Booking entity);
    Booking toEntity(BookingDTO dto);
    List<BookingDTO> toDtoList(List<Booking> list);
}
