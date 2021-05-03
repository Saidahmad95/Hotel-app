package com.example.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Integer id;
    private Integer number;
    private Integer floor;
    private Integer size;

    public RoomDTO(Integer number, Integer floor, Integer size) {
        this.number = number;
        this.floor = floor;
        this.size = size;

    }
}
