package io.agileinfra.trading.platform.iam.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.InterceptingHttpAccessor;

import java.util.List;

@Configuration
public class TradingPlatformSecurityClientConfiguration {
	@Bean
	public SecurityClientHelper securityClientHelper() {
		return new SecurityClientHelper();
	}

	@Bean
	public JwtTokenHttpInterceptor jwtTokenHttpInterceptor(final SecurityClientHelper securityClientHelper) {
		return new JwtTokenHttpInterceptor(securityClientHelper);
	}

	@Bean
	public JwtTokenHttpClientInitializer jwtTokenHttpClientInitializer(final JwtTokenHttpInterceptor jwtTokenHttpInterceptor,
			final List<InterceptingHttpAccessor> httpClients) {
		return new JwtTokenHttpClientInitializer(jwtTokenHttpInterceptor, httpClients);
	}

}
