package com.syncb.service;

import com.syncb.config.JwtUtil;
import com.syncb.entity.User;
import com.syncb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwt;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(String username, String password, String email) {
        // Check if username already exists
        if (userRepository.findOneByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid credentials");
        return jwt.generateToken(user.getUsername());
    }

    // Additional methods for image management
    public void addImageUrl(Long userId, String url) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getImageUrls().add(url);
            userRepository.save(user);
        }
    }

    public boolean removeImageUrl(Long userId, String url) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            boolean removed = user.getImageUrls().remove(url);
            if (removed) userRepository.save(user);
            return removed;
        }
        return false;
    }
}
