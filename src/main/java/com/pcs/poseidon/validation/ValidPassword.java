package com.pcs.poseidon.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Annotation to validate that a given field adheres to the password strength requirements.
 */
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

	String message() default "Password invalid.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}