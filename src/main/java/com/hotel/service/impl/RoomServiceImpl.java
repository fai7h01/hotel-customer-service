package com.hotel.service.impl;

import com.hotel.dto.RoomDTO;
import com.hotel.entity.Room;
import com.hotel.exception.RoomNotFoundException;
import com.hotel.mapper.RoomMapper;
import com.hotel.repository.RoomRepository;
import com.hotel.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        return roomMapper.toDto(roomRepository.save(roomMapper.toEntity(roomDTO)));
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toDto)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + id));
    }

    @Override
    public RoomDTO getRoomByNumber(String roomNumber) {
        var foundRoom = roomRepository.findRoomByRoomNumber(roomNumber)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with number: " + roomNumber));
        return roomMapper.toDto(foundRoom);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomMapper.toDtoList(roomRepository.findAll());
    }

    @Override
    public List<RoomDTO> getAvailableRooms() {
        return roomMapper.toDtoList(roomRepository.findAllByAvailable());
    }

    @Override
    public List<RoomDTO> getAvailableRoomsByDate(LocalDate checkInDate, LocalDate checkOutDate) {
        validateDates(checkInDate, checkOutDate);
        var rooms = roomRepository.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
        log.info("Available rooms for dates {} - {}: {}", checkInDate, checkOutDate, rooms);
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
