package com.cinema.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private List<TicketResponseDto> tickets;
    private String orderDateTime;
    private String userEmail;
}
