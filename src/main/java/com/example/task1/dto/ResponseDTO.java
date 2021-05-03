package com.example.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private Object object;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(Object object) {
        this.object = object;
    }
}
