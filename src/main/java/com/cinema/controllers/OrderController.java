package com.cinema.controllers;

import com.cinema.model.Order;
import com.cinema.model.ShoppingCart;
import com.cinema.model.dto.OrderResponseDto;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.service.mapper.OrderMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper,
                           UserService userService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/complete")
    public Order completeOrder(@RequestParam String email) {
        ShoppingCart shoppingCart = shoppingCartService
                .getByUser(userService.findByEmail(email).get());
        return orderService.completeOrder(shoppingCart.getTickets(), shoppingCart.getUser());
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistory(@RequestParam Long userId) {
        return orderService.getOrderHistory(userService.findById(userId).get()).stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
