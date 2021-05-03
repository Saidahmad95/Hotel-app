package com.example.task1.repository;

import com.example.task1.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {
   List<Room> findAllByHotelId(Integer hotel_id);

   Optional<Room> findAllByNumberAndHotelId(Integer number, Integer hotel_id);

   boolean existsByNumberAndHotelId(Integer number, Integer hotel_id);

   boolean existsByIdAndHotelId(Integer roomId, Integer hotel_id);
}
