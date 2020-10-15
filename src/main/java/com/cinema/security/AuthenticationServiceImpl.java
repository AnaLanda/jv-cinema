package com.cinema.security;

import com.cinema.dao.CinemaHallDao;
import com.cinema.exceptions.AuthenticationException;
import com.cinema.lib.Inject;
import com.cinema.lib.Service;
import com.cinema.model.User;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.util.HashUtil;
import org.apache.log4j.Logger;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AutheticationService {
    private static final Logger log = Logger.getLogger(AuthenticationServiceImpl.class);

    @Inject
    private UserService userService;

    @Inject
    private ShoppingCartService shoppingCartService;

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
