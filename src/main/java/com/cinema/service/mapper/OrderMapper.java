package com.cinema.service.mapper;

import com.cinema.model.Order;
import com.cinema.model.dto.OrderResponseDto;
import com.cinema.model.dto.TicketResponseDto;
import com.cinema.service.UserService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final TicketMapper ticketMapper;
    private final UserService userService;

    public OrderMapper(TicketMapper ticketMapper, UserService userService) {
        this.ticketMapper = ticketMapper;
        this.userService = userService;
    }

    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setId(order.getId());
        List<TicketResponseDto> tickets = order.getTickets().stream()
                .map(ticketMapper::mapToDto)
                .collect(Collectors.toList());
        orderDto.setTickets(tickets);
        orderDto.setOrderDateTime(
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        orderDto.setUserEmail(order.getUser().getEmail());
        return orderDto;
    }
}
