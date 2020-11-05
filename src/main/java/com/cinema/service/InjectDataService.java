package com.cinema.service;

import com.cinema.model.Role;
import com.cinema.security.AuthenticationService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InjectDataService {
    private final RoleService roleService;
    private final AuthenticationService authenticationService;

    public InjectDataService(RoleService roleService, AuthenticationService authService) {
        this.roleService = roleService;
        this.authenticationService = authService;
    }

    @PostConstruct
    public void injectData() {
        Role user = Role.of("USER");
        roleService.add(user);
        Role admin = roleService.add(Role.of("ADMIN"));
        authenticationService.register("admin-@gmail.com", "1234", Set.of(admin));
    }
}
