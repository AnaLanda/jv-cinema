package com.cinema.service.mapper;

import com.cinema.model.ShoppingCart;
import com.cinema.model.dto.ShoppingCartResponseDto;
import com.cinema.model.dto.TicketResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    private final TicketMapper ticketMapper;

    public ShoppingCartMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto shoppingCartDto = new ShoppingCartResponseDto();
        shoppingCartDto.setId(shoppingCart.getId());
        List<TicketResponseDto> ticketDtos = shoppingCart.getTickets().stream()
                .map(ticketMapper::mapToDto)
                .collect(Collectors.toList());
        shoppingCartDto.setTickets(ticketDtos);
        shoppingCartDto.setUserEmail(shoppingCart.getUser().getEmail());
        return shoppingCartDto;
    }
}
