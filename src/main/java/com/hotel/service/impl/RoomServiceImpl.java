package com.hotel.service.impl;

import com.hotel.dto.RoomDTO;
import com.hotel.exception.RoomNotFoundException;
import com.hotel.mapper.RoomMapper;
import com.hotel.repository.RoomRepository;
import com.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomDTO getRoomByNumber(String roomNumber) {
        var foundRoom = roomRepository.findRoomByRoomNumber(roomNumber)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with number: " + roomNumber));
        return roomMapper.toDto(foundRoom);
    }

    @Override
    public List<RoomDTO> getAvailableRooms() {
        return roomMapper.toDtoList(roomRepository.findAllByAvailable());
    }

    @Override
    public List<RoomDTO> getAvailableRoomsByDate(LocalDate checkInDate, LocalDate checkOutDate) {
        validateDates(checkInDate, checkOutDate);
        var rooms = roomRepository.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
        return roomMapper.toDtoList(rooms);
    }

    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }

        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
    }
}
