package com.cinema.model.dto;

import lombok.Data;

@Data
public class TicketResponseDto {
    private Long id;
    private Long movieSessionId;
    private String userEmail;
}
