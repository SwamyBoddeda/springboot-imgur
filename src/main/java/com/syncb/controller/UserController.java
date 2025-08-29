package com.syncb.controller;

import com.syncb.entity.User;
import com.syncb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    record RegisterUserRequest(String username, String password, String email) {}
    record LoginUserRequest(String username, String password) { }
    record AuthToken(String token) { }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest RegisterUserRequest) {
        try {
            User registeredUser = userService.register(RegisterUserRequest.username(),
                                                        RegisterUserRequest.password(),
                                                        RegisterUserRequest.email());
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public AuthToken login(@RequestBody LoginUserRequest loginRequest) {
        return new AuthToken(userService.authenticate(loginRequest.username(), loginRequest.password()));
    }

    @GetMapping("/{userId}/images")
    public ResponseEntity<?> getUserImages(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getImages());
    }


}
