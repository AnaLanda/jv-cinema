package com.cinema.controllers;

import com.cinema.model.ShoppingCart;
import com.cinema.model.dto.MovieSessionRequestDto;
import com.cinema.model.dto.ShoppingCartResponseDto;
import com.cinema.service.MovieSessionService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.service.mapper.MovieSessionMapper;
import com.cinema.service.mapper.ShoppingCartMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final MovieSessionService movieSessionService;
    private final UserService userService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final MovieSessionMapper movieSessionMapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  MovieSessionService sessionService,
                                  UserService userService,
                                  ShoppingCartMapper shoppingCartMapper,
                                  MovieSessionMapper movieSessionMapper) {
        this.shoppingCartService = shoppingCartService;
        this.movieSessionService = sessionService;
        this.userService = userService;
        this.shoppingCartMapper = shoppingCartMapper;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping("/movie-sessions")
    public void addMovieSession(@RequestParam Long userId,
                                        @RequestBody MovieSessionRequestDto dto) {
        shoppingCartService.addSession(movieSessionMapper.mapToMovieSession(dto),
                userService.findById(userId).get());
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(@RequestParam Long userId) {
        ShoppingCart cart = shoppingCartService.getByUser(userService.findById(userId).get());
        return shoppingCartMapper.mapToDto(cart);
    }
}
