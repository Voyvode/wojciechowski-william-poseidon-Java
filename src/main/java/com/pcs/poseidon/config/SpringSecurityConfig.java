package com.pcs.poseidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class SpringSecurityConfig {

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

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}