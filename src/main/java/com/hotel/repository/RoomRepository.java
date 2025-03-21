package com.hotel.repository;

import com.hotel.entity.Room;
import com.hotel.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByRoomType(RoomType roomType);

    Optional<Room> findRoomByRoomNumber(String number);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT b.room.id FROM Booking b " +
            "WHERE b.status != 'CANCELLED' " +
            "AND b.checkInDate <= CURRENT_DATE " +
            "AND b.checkOutDate >= CURRENT_DATE)")
    List<Room> findAllByAvailable();

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT b.room.id FROM Booking b " +
            "WHERE b.status != 'CANCELLED' " +
            "AND b.checkInDate <= :checkOutDate " +
            "AND b.checkOutDate >= :checkInDate)")
    List<Room> findAvailableRoomsByDateRange(@Param("checkInDate") LocalDate checkInDate,
                                             @Param("checkOutDate") LocalDate checkOutDate);

}
