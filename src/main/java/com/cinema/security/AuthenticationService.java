package com.cinema.security;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.model.Role;
import com.cinema.model.User;
import java.util.Set;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password, Set<Role> roles);
}
