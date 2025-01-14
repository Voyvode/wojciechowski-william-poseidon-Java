package com.pcs.poseidon.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * A validator class that checks if a given password satisfies the required constraints.
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	/**
	 * Criteria applied by this RegEx:
	 * <ul>
	 * <li>At least 1 uppercase letter. <code>(?=.*[A-Z])</code>
	 * <li>At least 1 digit. <code>(?=.*\\d)</code>
	 * <li>At least 1 special character. <code>(?=.*[@#$%^&+=!])</code>
	 * <li>No spaces. <code>(?=\\S+$)</code>
	 * <li>Minimum 8 characters length. <code>.{8,}</code>
	 * </ul>
	 */
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(
			"^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
	);

	/**
	 * Validates that the password meets the criteria.
	 *
	 * @param password the password to validate
	 * @return true if the password is valid, false otherwise
	 */
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null) {
			return false;
		}

		return PASSWORD_PATTERN.matcher(password).matches();
	}

}