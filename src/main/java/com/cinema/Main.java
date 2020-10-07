package com.cinema;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.lib.Injector;
import com.cinema.model.User;
import com.cinema.security.AutheticationService;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) throws AuthenticationException {
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
