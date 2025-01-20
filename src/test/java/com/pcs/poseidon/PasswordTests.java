package com.pcs.poseidon;

import com.pcs.poseidon.validation.PasswordConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class PasswordTests {

    private PasswordConstraintValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PasswordConstraintValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testPassword() {
        var encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";

        String encodedPassword = encoder.encode("123456");
        assertTrue(encoder.matches(rawPassword, encodedPassword));

        String anotherEncodedPassword = encoder.encode(rawPassword);
        assertNotEquals(encodedPassword, anotherEncodedPassword);
    }

    @Test
    void testIsValid_withValidPassword() {
        String validPassword = "Password1!";
        boolean result = validator.isValid(validPassword, context);
        assertTrue(result);
    }

    @Test
    void testIsValid_withNullPassword() {
        boolean result = validator.isValid(null, context);
        assertFalse(result);
    }

    @Test
    void testIsValid_withPasswordMissingUppercase() {
        String invalidPassword = "password1!";
        boolean result = validator.isValid(invalidPassword, context);
        assertFalse(result);
    }

    @Test
    void testIsValid_withPasswordMissingDigit() {
        String invalidPassword = "Password!!";
        boolean result = validator.isValid(invalidPassword, context);
        assertFalse(result);
    }

    @Test
    void testIsValid_withPasswordMissingSpecialCharacter() {
        String invalidPassword = "Password1";
        boolean result = validator.isValid(invalidPassword, context);
        assertFalse(result);
    }

    @Test
    void testIsValid_withPasswordContainingWhitespace() {
        String invalidPassword = "Password 1!";
        boolean result = validator.isValid(invalidPassword, context);
        assertFalse(result);
    }

    @Test
    void testIsValid_withPasswordShorterThanRequiredLength() {
        String invalidPassword = "P1!";
        boolean result = validator.isValid(invalidPassword, context);
        assertFalse(result);
    }

}
