package com.cinema.security;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.model.Role;
import com.cinema.model.User;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = Logger.getLogger(AuthenticationServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder, UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email).get();
        log.info("A user with the email " + email + " is trying to log in");
        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("The user with the email " + email + " has logged in");
            return user;
        } else {
            throw new AuthenticationException("Incorrect email or password");
        }
    }

    @Override
    public User register(String email, String password, Set<Role> roles) {
        log.info("Trying to register a new user with the email " + email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        log.info("Successfully registered a new user with the email " + email);
        return user;
    }
}
