package com.insurance.management.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequestDto request) {
        String tempPassword = userService.createUser(request);
        return ResponseEntity.ok(tempPassword);
    }
}
