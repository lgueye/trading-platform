package io.agileinfra.trading.platform.market.api;

import io.agileinfra.trading.platform.market.model.MarketApi;
import io.agileinfra.trading.platform.shared.api.TradingPlatformSharedApiConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(TradingPlatformSharedApiConfiguration.class)
public class MarketApiConfiguration {
	@Bean
	public MarketApi marketApi(@Value("${market.server.url}") final String apiUrl, final RestTemplate restTemplate) {
		return new MarketApiImpl(apiUrl, restTemplate);
	}
}
