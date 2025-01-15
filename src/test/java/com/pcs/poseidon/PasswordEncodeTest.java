package com.pcs.poseidon;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordEncodeTest {

    @Test
    public void testPassword() {
        var encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";

        String encodedPassword = encoder.encode("123456");
        assertTrue(encoder.matches(rawPassword, encodedPassword));

        String anotherEncodedPassword = encoder.encode(rawPassword);
		assertNotEquals(encodedPassword, anotherEncodedPassword);
    }

}
