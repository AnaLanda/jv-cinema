package com.cinema.security;

import com.cinema.model.Role;
import com.cinema.model.User;
import java.util.Set;

public interface AuthenticationService {
    User register(String email, String password, Set<Role> roles);
}
