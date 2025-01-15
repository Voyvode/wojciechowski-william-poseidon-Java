package com.pcs.poseidon;

import com.pcs.poseidon.validation.PasswordConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class PasswordStrengthTest {

    private PasswordConstraintValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PasswordConstraintValidator();
        context = mock(ConstraintValidatorContext.class);
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
