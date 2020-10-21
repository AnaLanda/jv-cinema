package com.cinema.security;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.model.User;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.util.HashUtil;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = Logger.getLogger(AuthenticationServiceImpl.class);
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        log.info("Trying to log in the user with the email " + email);
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && HashUtil.isValid(password, userFromDB.get())) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Incorrect user name or password.");
    }

    @Override
    public User register(String email, String password) {
        log.info("Trying to register a new user with the email " + email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        log.info("Successfully registered a new user with the email " + email);
        return user;
    }
}
