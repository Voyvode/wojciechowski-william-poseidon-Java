package com.pcs.poseidon.config;

import java.sql.SQLException;

import org.h2.tools.Server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pcs.poseidon.domain.Bid;
import com.pcs.poseidon.domain.CurvePoint;
import com.pcs.poseidon.domain.Rating;
import com.pcs.poseidon.domain.Rule;
import com.pcs.poseidon.domain.Trade;
import com.pcs.poseidon.domain.User;
import com.pcs.poseidon.repositories.BidRepository;
import com.pcs.poseidon.repositories.CurvePointRepository;
import com.pcs.poseidon.repositories.RatingRepository;
import com.pcs.poseidon.repositories.RuleRepository;
import com.pcs.poseidon.repositories.TradeRepository;
import com.pcs.poseidon.repositories.UserRepository;

/**
 * A Spring configuration class used to set up and initialize the application
 * in a "local" Spring profile.
 * <p>
 * This configuration includes:
 * <ul>
 * <li>Security chain definition with rules for user authorization.
 * <li>Password encoding using BCryptPasswordEncoder.
 * <li>Starting an internal H2 database server for local development convenience.
 * <li>Initializing a development database with sample data.
 * </ul>
 */
@Configuration
@Profile("local")
public class LocalConfig {

	/**
	 * Configures a SecurityFilterChain bean that defines security settings for HTTP requests.
	 * Provides authorization rules, form login configuration, and a custom logout flow.
	 * <p>
	 * Similar to {@link SpringSecurityConfig} but with CSRF disabled for convenience.
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
					auth.anyRequest().hasAnyRole("ADMIN", "USER");
				})
				.formLogin(Customizer.withDefaults())
				.logout(logout ->
						logout.logoutUrl("/app-logout")
								.logoutSuccessUrl("/login")
								.invalidateHttpSession(true)
								.permitAll()
				)
				.csrf(AbstractHttpConfigurer::disable)
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

	/**
	 * Starts an internal H2 server to allow querying from the IDE.
	 *
	 * @return the H2 server instance
	 * @throws SQLException if an SQL error occurs while starting the server
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	protected Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

	/**
	 * Initializes the development database with sample data.
	 *
	 * @param userRepo       the repository for managing users
	 * @param bidRepo        the repository for managing bids
	 * @param ruleRepo       the repository for managing rules
	 * @param tradeRepo      the repository for managing trades
	 * @param ratingRepo     the repository for managing ratings
	 * @param curvePointRepo the repository for managing curve points
	 * @return a CommandLineRunner that populates the database with sample data
	 */
	@Bean
	protected CommandLineRunner initDevDatabase(
			UserRepository userRepo,
			BidRepository bidRepo,
			RuleRepository ruleRepo,
			TradeRepository tradeRepo,
			RatingRepository ratingRepo,
			CurvePointRepository curvePointRepo
	) {
		return args -> {
			var passwordEncoder = new BCryptPasswordEncoder();

			var admin = new User();
			admin.setFullname("Administrator");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("123456"));
			admin.setRole("ADMIN");
			userRepo.save(admin);

			var user = new User();
			user.setFullname("User");
			user.setUsername("user");
			user.setPassword(passwordEncoder.encode("123456"));
			user.setRole("USER");
			userRepo.save(user);

			for (int i = 1; i <= 20; i++) {
				var bid = new Bid();
				bid.setAccount("Account" + i);
				bid.setType("Type" + i);
				bid.setBidQuantity((double) (i * 10));
				bidRepo.save(bid);
			}

			for (int i = 1; i <= 20; i++) {
				var rule = new Rule();
				rule.setName("Rule" + i);
				rule.setDescription("Description for Rule " + i);
				rule.setJson("{\"key\": \"value" + i + "\"}");
				rule.setTemplate("Template" + i);
				rule.setSqlStr("SELECT * FROM table" + i);
				rule.setSqlPart("WHERE id = " + i);
				ruleRepo.save(rule);
			}

			for (int i = 1; i <= 20; i++) {
				var trade = new Trade();
				trade.setAccount("Account" + i);
				trade.setType("Type" + i);
				trade.setBuyQuantity((double) (i * 100));
				tradeRepo.save(trade);
			}

			for (int i = 1; i <= 20; i++) {
				var rating = new Rating();
				rating.setMoodysRating("Moody's Rating " + i);
				rating.setSandpRating("S&P Rating " + i);
				rating.setFitchRating("Fitch Rating " + i);
				rating.setOrderNumber((long) i);
				ratingRepo.save(rating);
			}

			for (int i = 1; i <= 20; i++) {
				var curvePoint = new CurvePoint();
				curvePoint.setCurveId((long) i);
				curvePoint.setTerm((double) i);
				curvePoint.setValue((double) (i * 2));
				curvePointRepo.save(curvePoint);
			}
		};
	}

}