package com.cinema.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieRequestDto {
    @NotNull(message = "The movie title can't be null.")
    private String title;
    private String description;
}
