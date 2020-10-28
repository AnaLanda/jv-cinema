package com.cinema.service.mapper;

import com.cinema.model.Order;
import com.cinema.model.dto.OrderResponseDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setId(order.getId());
        List<Long> ticketIds = order.getTickets().stream()
                .map(ticket -> ticket.getId())
                .collect(Collectors.toList());
        orderDto.setTicketIds(ticketIds);
        orderDto.setOrderDateTime(
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        orderDto.setUserId(order.getUser().getId());
        return orderDto;
    }
}
