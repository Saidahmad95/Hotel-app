package com.example.task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer number;

    private Integer floor;

    private Integer size;

    @ManyToOne
    private Hotel hotel;

    public Room(Integer number, Integer floor, Integer size, Hotel hotel) {
        this.number = number;
        this.floor = floor;
        this.size = size;
        this.hotel = hotel;
    }
}
