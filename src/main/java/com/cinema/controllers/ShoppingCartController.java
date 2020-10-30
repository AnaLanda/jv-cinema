package com.cinema.controllers;

import com.cinema.model.ShoppingCart;
import com.cinema.model.User;
import com.cinema.model.dto.ShoppingCartResponseDto;
import com.cinema.service.MovieSessionService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.service.mapper.MovieSessionMapper;
import com.cinema.service.mapper.ShoppingCartMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void addMovieSession(Authentication authentication,
                                @RequestParam Long movieSessionId) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(details.getUsername()).get();
        shoppingCartService.addSession(movieSessionService.findById(movieSessionId), user);
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(details.getUsername()).get();
        ShoppingCart cart = shoppingCartService.getByUser(user);
        return shoppingCartMapper.mapToDto(cart);
    }
}
