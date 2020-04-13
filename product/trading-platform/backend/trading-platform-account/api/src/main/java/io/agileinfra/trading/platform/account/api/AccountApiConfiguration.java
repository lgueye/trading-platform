package io.agileinfra.trading.platform.account.api;

import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.shared.api.TradingPlatformSharedApiConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(TradingPlatformSharedApiConfiguration.class)
public class AccountApiConfiguration {
	@Bean
	public AccountApi accountApi(@Value("${account.server.url}") final String apiUrl, final RestTemplate restTemplate) {
		return new AccountApiImpl(apiUrl, restTemplate);
	}

	@Bean
	public OrderEventApi orderEventApi(@Value("${account.server.url}") final String apiUrl, final RestTemplate restTemplate) {
		return new OrderEventApiImpl(apiUrl, restTemplate);
	}
}
