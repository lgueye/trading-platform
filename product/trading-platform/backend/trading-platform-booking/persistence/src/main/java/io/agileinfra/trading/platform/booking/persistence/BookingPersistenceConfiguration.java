package io.agileinfra.trading.platform.booking.persistence;

import io.agileinfra.trading.platform.account.api.AccountApiConfiguration;
import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.instrument.api.InstrumentApiConfiguration;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.persistence.configuration.TradingPlatformPersistenceConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"io.agileinfra.trading.platform.booking.persistence"})
@EnableJpaRepositories(basePackages = {"io.agileinfra.trading.platform.booking.persistence"})
@Configuration
@Import({TradingPlatformPersistenceConfiguration.class, AccountApiConfiguration.class, InstrumentApiConfiguration.class})
public class BookingPersistenceConfiguration {
	@Bean
	public BookingPersistenceService bookingPersistenceService(final BookingRepository repository, OrderEventApi orderEventApi,
			InstrumentApi instrumentApi) {
		return new BookingPersistenceService(repository, orderEventApi, instrumentApi);
	}
}
