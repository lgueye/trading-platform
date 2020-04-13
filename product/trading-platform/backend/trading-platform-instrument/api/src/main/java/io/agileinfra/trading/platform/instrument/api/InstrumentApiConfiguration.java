package io.agileinfra.trading.platform.instrument.api;

import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.shared.api.TradingPlatformSharedApiConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(TradingPlatformSharedApiConfiguration.class)
public class InstrumentApiConfiguration {
	@Bean
	public InstrumentApi instrumentApi(@Value("${instrument.server.url}") final String apiUrl, final RestTemplate restTemplate) {
		return new InstrumentApiImpl(apiUrl, restTemplate);
	}
}
