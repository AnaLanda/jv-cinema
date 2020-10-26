package com.cinema.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequestDto {
    private List<TicketResponseDto> tickets;
    private String orderDateTime;
    private String userEmail;
}
