package io.agileinfra.trading.platform.iam.configuration.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.shared.configuration.serialization.TradingPlatformSharedSerializationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Duration;

@Import({TradingPlatformSharedSerializationConfiguration.class, ClockApiConfiguration.class})
@Configuration
public class WebSecurityConfiguration {

	@Bean
	public JwtProvider jwtProvider(@Value("${jwt.secret.key:reKcQ3xZY12kbHyJhyikXLq7oXozsXvc}") final String jwtSecretKey,
			@Value("${jwt.expiration:P30D}") final Duration expiration, final ObjectMapper objectMapper) {
		return new JwtProvider(jwtSecretKey, expiration, objectMapper);
	}

	@Bean
	public JwtFilter jwtFilter(final JwtProvider jwtProvider, final ClockApi clockApi) {
		return new JwtFilter(jwtProvider, clockApi);
	}

	@Bean
	public JwtFilterConfigurer jwtFilterConfigurer(final JwtFilter jwtFilter) {
		return new JwtFilterConfigurer(jwtFilter);
	}

}
