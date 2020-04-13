package io.agileinfra.trading.platform.booking.api;

import io.agileinfra.trading.plateform.booking.model.BookingApi;
import io.agileinfra.trading.platform.shared.api.TradingPlatformSharedApiConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(TradingPlatformSharedApiConfiguration.class)
public class BookingApiConfiguration {
	@Bean
	public BookingApi bookingApi(@Value("${booking.server.url}") final String apiUrl, final RestTemplate restTemplate) {
		return new BookingApiImpl(apiUrl, restTemplate);
	}
}
