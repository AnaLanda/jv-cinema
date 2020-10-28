package com.cinema.service.mapper;

import com.cinema.model.ShoppingCart;
import com.cinema.model.dto.ShoppingCartResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {

    public ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto shoppingCartDto = new ShoppingCartResponseDto();
        List<Long> ticketIds = shoppingCart.getTickets().stream()
                .map(ticket -> ticket.getId())
                .collect(Collectors.toList());
        shoppingCartDto.setTicketIds(ticketIds);
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
        return shoppingCartDto;
    }
}
