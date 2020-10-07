package com.cinema;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.lib.Injector;
import com.cinema.model.User;
import com.cinema.security.AutheticationService;
import com.cinema.service.UserService;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");
    private static final String DATE = "20201021";
    private static final String DATE_TIME = "2020-10-21T10:15:30";

    public static void main(String[] args) throws AuthenticationException {
        UserService userService = (UserService) injector.getInstance(UserService.class);
        AutheticationService autheticationService =
                (AutheticationService) injector.getInstance(AutheticationService.class);
        User user1 = new User();
        user1.setEmail("user@gmail.com");
        user1.setPassword("password");
        System.out.println("User1 has been registered: "
                + autheticationService.register(user1.getEmail(), user1.getPassword()));
        System.out.println("User1 has logged in: "
                + autheticationService.login(user1.getEmail(), user1.getPassword()));
    }
}
