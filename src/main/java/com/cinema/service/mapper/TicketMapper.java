package com.cinema.service.mapper;

import com.cinema.model.Ticket;
import com.cinema.model.dto.TicketResponseDto;
import com.cinema.service.MovieSessionService;
import com.cinema.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;
    private final UserService userService;

    public TicketMapper(MovieSessionService movieSessionService,
                        MovieSessionMapper movieSessionMapper,
                        UserService userService) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
        this.userService = userService;
    }

    public TicketResponseDto mapToDto(Ticket ticket) {
        TicketResponseDto ticketDto = new TicketResponseDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setMovieSessionId(ticket.getMovieSession().getId());
        ticketDto.setUserEmail(ticket.getUser().getEmail());
        return ticketDto;
    }
}
