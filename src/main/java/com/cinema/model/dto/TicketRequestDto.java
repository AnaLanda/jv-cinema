package com.cinema.model.dto;

import lombok.Data;

@Data
public class TicketRequestDto {
    private MovieSessionRequestDto movieSessionRequestDto;
    private String userEmail;
}
