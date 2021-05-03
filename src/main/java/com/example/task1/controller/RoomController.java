package com.example.task1.controller;

import com.example.task1.dto.ResponseDTO;
import com.example.task1.dto.RoomDTO;
import com.example.task1.entity.Hotel;
import com.example.task1.entity.Room;
import com.example.task1.repository.HotelRepository;
import com.example.task1.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;

    // DB dagi barcha roomlarni olish
    @GetMapping("/getAllHotelsRooms")
    public List<Room> getAllHotelsRooms() {
        return roomRepository.findAll();
    }

    // Roomlarni Hotel id bo'yicha olish
    @GetMapping("/getAllRoomsByHotels_Id/{id}")
    public ResponseDTO getAllRoomsByHotels_Id(@PathVariable Integer id) {
        if (hotelRepository.existsById(id)) {
            return new ResponseDTO("RESULT: ", roomRepository.findAllByHotelId(id));
        }
        return new ResponseDTO("Hotel with id: '" + id + "' not found !");
    }

    // Roomlarni room id bo'yicha olish
    @GetMapping(value = "/getRoomById/{roomId}")
    public Room getRoomById(@PathVariable Integer roomId) {
        Optional<Room> byRoomId = roomRepository.findById(roomId);
        return byRoomId.orElseGet(Room::new);
    }

    // Roomlarni hotel id va room number bo'yicha olish
    @GetMapping(value = "/getRoomByIdAndHotel_id/{hotelId}/{roomNumber}")
    public ResponseDTO getRoomByIdAndHotel_id(@PathVariable Integer hotelId, @PathVariable Integer roomNumber) {
        if (hotelRepository.existsById(hotelId)) {
            Optional<Room> allByNumberAndHotelId = roomRepository.findAllByNumberAndHotelId(roomNumber, hotelId);
            if (allByNumberAndHotelId.isPresent()) {
                return new ResponseDTO("Found !" + allByNumberAndHotelId.get());
            }
            return new ResponseDTO("Hotel: '" + hotelRepository.getOne(hotelId).getName() + "' does not have room with number: '" + roomNumber + "'");
        }
        return new ResponseDTO("Hotel with id: '" + hotelId + "' not found !");
    }


    @PostMapping(value = "/addRoom/{hotelId}")
    public String addRoom(@RequestBody RoomDTO roomDTO, @PathVariable Integer hotelId) {
        Optional<Hotel> byIdHotel = hotelRepository.findById(hotelId);
        if (byIdHotel.isPresent()) {
            boolean existsRoom = roomRepository.existsByNumberAndHotelId(roomDTO.getNumber(), hotelId);
            if (existsRoom) {
                return "Hotel : '" + byIdHotel.get().getName() + "' , already have room with number: " + roomDTO.getNumber();
            }
            Room newRoom = new Room(roomDTO.getNumber(), roomDTO.getFloor(), roomDTO.getSize(), byIdHotel.get());
            roomRepository.save(newRoom);
            return "Room with number: '" + roomDTO.getNumber() + "' successfully added into hotel: '" + byIdHotel.get().getName() + "'";
        }
        return "Hotel with id: '" + hotelId + "' not found !";
    }


    @PutMapping(value = "/editRoom/{hotelId}/{roomNumber}")
    public String editRoom(@PathVariable Integer hotelId, @PathVariable Integer roomNumber, @RequestBody RoomDTO roomDTO) {
        Optional<Hotel> byIdHotel = hotelRepository.findById(hotelId);
        if (byIdHotel.isPresent()) {
            Optional<Room> allByNumberAndHotelId = roomRepository.findAllByNumberAndHotelId(roomNumber, hotelId);
            if (allByNumberAndHotelId.isPresent()) {
                boolean b = roomRepository.existsByNumberAndHotelId(roomDTO.getNumber(), hotelId);
                if (b) {
                    return "Hotel: '" + byIdHotel.get().getName() + "' already has room with number:'" + roomDTO.getNumber() + "' !";
                }
                Room room = allByNumberAndHotelId.get();
                room.setNumber(roomDTO.getNumber());
                room.setFloor(roomDTO.getFloor());
                room.setSize(roomDTO.getSize());
                roomRepository.save(room);
                return "'" + byIdHotel.get().getName() + "' hotel's room with old number: '" + roomNumber + "' successfully edited !";
            }
            return "'" + byIdHotel.get().getName() + "' hotel does not have room with number: '" + roomNumber + "' !";
        }
        return "Hotel with id: '" + hotelId + "' , not found !";
    }


    @DeleteMapping(value = "/deleteRoom/{hotelId}/{roomId}")
    public String deleteRoom(@PathVariable Integer hotelId, @PathVariable Integer roomId) {
        Optional<Hotel> byIdHotel = hotelRepository.findById(hotelId);
        if (byIdHotel.isPresent()) {
            if (roomRepository.existsByIdAndHotelId(roomId, hotelId)) {
                roomRepository.deleteById(roomId);
                return "'" + byIdHotel.get().getName() + "' hotel's room with id: '" + roomId + "' successfully deleted !";
            }
            return "'" + byIdHotel.get().getName() + "' hotel does not have room with id: '" + roomId + "' !";
        }
        return "Hotel with id: '" + hotelId + "' not found !";
    }

}

