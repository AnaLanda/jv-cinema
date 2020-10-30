package com.cinema.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @NotNull(message = "The movie ID can't be null.")
    private Long movieId;
    @NotNull(message = "The cinema hall ID can't be null.")
    private Long cinemaHallId;
    @NotNull(message = "The show time can't be null.")
    private String showTime;
}
