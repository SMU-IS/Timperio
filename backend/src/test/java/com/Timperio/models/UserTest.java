package com.Timperio.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.Timperio.enums.Role;

public class UserTest {

    @Test
    public void testUserCreation() {
        MarketingUser user = new MarketingUser();
        user.setUserId(1);
        user.setUserEmail("test@example.com");
        user.setPassword("password123");
        user.setUserName("Test User");

        assertEquals(1, user.getUserId());
        assertEquals("test@example.com", user.getUserEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("Test User", user.getUserName());
        assertEquals(Role.MARKETING, user.getRole());

        AdminUser user2 = new AdminUser();
        user2.setUserId(2);
        user2.setUserEmail("test2@example.com");
        user2.setPassword("password1234");
        user2.setUserName("Test User 2");

        assertEquals(2, user2.getUserId());
        assertEquals("test2@example.com", user2.getUserEmail());
        assertEquals("password1234", user2.getPassword());
        assertEquals("Test User 2", user2.getUserName());
        assertEquals(Role.ADMIN, user2.getRole());

        SalesUser user3 = new SalesUser();
        user3.setUserId(3);
        user3.setUserEmail("test3@example.com");
        user3.setPassword("password12345");
        user3.setUserName("Test User 3");

        assertEquals(3, user3.getUserId());
        assertEquals("test3@example.com", user3.getUserEmail());
        assertEquals("password12345", user3.getPassword());
        assertEquals("Test User 3", user3.getUserName());
        assertEquals(Role.SALES, user3.getRole());
    }
}

