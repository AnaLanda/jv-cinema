package com.cinema.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
    @Min(20)
    private int capacity;
    @NotNull(message = "The cinema hall description can't be null.")
    private String description;
}
