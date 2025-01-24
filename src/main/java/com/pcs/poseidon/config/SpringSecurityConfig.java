package com.pcs.poseidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security in the production profile.
 * This class is responsible for configuring security mechanisms such as HTTP request authorization,
 * form login, logout functionality, and password encoding.
 */
@Configuration
@EnableWebSecurity
@Profile("prod")
public class SpringSecurityConfig {

	/**
	 * Configures a SecurityFilterChain bean that defines security settings for HTTP requests.
	 * Provides authorization rules, form login configuration, and a custom logout flow.
	 *
	 * @param http the HttpSecurity object used to configure the security settings
	 * @return the configured SecurityFilterChain instance
	 * @throws Exception if an error occurs during the configuration of the SecurityFilterChain
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/admin/**").hasRole("ADMIN");
					auth.requestMatchers("/user").hasRole("USER");
					auth.anyRequest().authenticated();
				})
				.formLogin(Customizer.withDefaults()).logout(logout ->
						logout.logoutUrl("/app-logout")
								.logoutSuccessUrl("/login")
								.invalidateHttpSession(true)
								.permitAll()
				)
				.build();
	}

	/**
	 * Creates a {@link BCryptPasswordEncoder} instance for encoding and verifying passwords.
	 *
	 * @return a configured instance of {@link BCryptPasswordEncoder}
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}