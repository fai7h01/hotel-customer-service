package com.hotel.service;

import com.hotel.dto.RoomDTO;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

   // RoomDTO createRoom(RoomDTO roomDTO);
    //RoomDTO getRoomById(Long id);
    RoomDTO getRoomByNumber(String roomNumber);
   // List<RoomDTO> getAllRooms();
    List<RoomDTO> getAvailableRooms();
    List<RoomDTO> getAvailableRoomsByDate(LocalDate checkInDate, LocalDate checkOutDate);
}
