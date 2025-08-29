package com.syncb;

import com.syncb.entity.User;
import com.syncb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        User registered = userService.register("testuser", "testpass", "test@example.com");
        assertNotNull(registered);
        assertEquals("testuser", registered.getUsername());
        assertEquals("testpass", registered.getPassword());
    }

    @Test
    public void testFindByUsername() {
        Optional<User> found = userService.findByUsername("testuser");
        assertNotNull(found);
        assertEquals("testuser", found.get().getUsername());
    }
}
