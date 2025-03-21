package com.hotel.mapper;

import com.hotel.dto.RoomDTO;
import com.hotel.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDTO toDto(Room entity);
    Room toEntity(RoomDTO dto);
    List<RoomDTO> toDtoList(List<Room> list);
}
