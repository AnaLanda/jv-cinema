package com.cinema.model.dto;

import lombok.Data;

@Data
public class TicketResponseDto {
    private Long id;
    private MovieSessionResponseDto movieSessionResponseDto;
    private String userEmail;
}
