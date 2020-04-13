package io.agileinfra.trading.platform.iam.configuration.server;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Import(WebSecurityConfiguration.class)
public class TradingPlatformSecurityServerConfiguration extends WebSecurityConfigurerAdapter {

	private final JwtFilterConfigurer jwtFilterConfigurer;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable CSRF (cross site request forgery)
		http.csrf().disable();

		// No session will be created or used by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Entry points
		http.authorizeRequests()//
				.antMatchers("/actuator/**").permitAll()//
				.antMatchers("/api/v1/iam/**").permitAll()//
				.antMatchers("/api/v1/accounts/**").permitAll()//
				// Disallow everything else..
				.anyRequest().permitAll();

		// Apply JWT
		http.apply(jwtFilterConfigurer);
	}
}
