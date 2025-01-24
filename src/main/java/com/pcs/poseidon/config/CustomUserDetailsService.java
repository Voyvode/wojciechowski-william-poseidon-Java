package com.pcs.poseidon.config;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pcs.poseidon.repositories.UserRepository;

/**
 * Provides user authentication details to the Spring Security framework, retrieving user-specific
 * data from the data source and transforming it into a {@link UserDetails} object.
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * Loads user details by username from the data source for authentication.
	 *
	 * @param username The username of the user.
	 * @return A {@link UserDetails} containing the user's credentials and authorities.
	 * @throws UsernameNotFoundException if the user is not found.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username);
		return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
	}

	/**
	 * Converts a given role into a list of granted authorities.
	 * The method prefixes the role with "ROLE_" to adhere to Spring Security's convention.
	 *
	 * @param role The role of the user to convert into granted authorities.
	 * @return A list of {@link GrantedAuthority} objects representing the user's role.
	 */
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

}