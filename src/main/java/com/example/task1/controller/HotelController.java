package com.example.task1.controller;

import com.example.task1.entity.Hotel;
import com.example.task1.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/hotel")
@RestController
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping(value = "/getAllHotels")
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping(value = "/getHotelById/{id}")
    public Hotel getHotelById(@PathVariable Integer id) {
        Optional<Hotel> byId = hotelRepository.findById(id);
        return byId.orElseGet(Hotel::new);

    }

    @PostMapping(value = "/addHotel")
    public String addHotel(@RequestBody Hotel hotel) {
        if (hotelRepository.existsByName(hotel.getName())) {
            return "Hotel with name: '" + hotel.getName() + "' already exists !";
        }
        Hotel newHotel = new Hotel(hotel.getName());
        return "Hotel with name: '" + hotel.getName() + "' successfully added";
    }

    @PutMapping(value = "/editHotel/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> byIdHotel = hotelRepository.findById(id);
        if (byIdHotel.isPresent()) {
            if (hotelRepository.existsByName(hotel.getName())) {
                return "Hotel with name: '" + hotel.getName() + "' already exists !";
            }
            Hotel editHotel = byIdHotel.get();
            editHotel.setName(hotel.getName());
            hotelRepository.save(editHotel);
            return "Hotel with name: '" + hotel.getName() + "' successfully edited !";
        }
        return "Hotel with id: '" + id + "' not found !";
    }

    @DeleteMapping(value = "/deleteMapping/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return "Hotel with id: '" + id + "' successfully deleted !";
        }
        return "Hotel with id: '" + id + "' not found !";
    }

}
