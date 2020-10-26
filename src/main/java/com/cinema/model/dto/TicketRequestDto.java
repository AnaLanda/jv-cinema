package com.cinema.model.dto;

import lombok.Data;

@Data
public class TicketRequestDto {
    private Long movieSessionId;
    private String userEmail;
}
