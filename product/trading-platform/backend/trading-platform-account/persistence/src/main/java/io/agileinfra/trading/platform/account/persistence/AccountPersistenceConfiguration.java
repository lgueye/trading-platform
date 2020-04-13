package io.agileinfra.trading.platform.account.persistence;

import io.agileinfra.trading.platform.instrument.api.InstrumentApiConfiguration;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.persistence.configuration.TradingPlatformPersistenceConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"io.agileinfra.trading.platform.account.persistence"})
@EnableJpaRepositories(basePackages = {"io.agileinfra.trading.platform.account.persistence"})
@Configuration
@Import({TradingPlatformPersistenceConfiguration.class, InstrumentApiConfiguration.class})
public class AccountPersistenceConfiguration {
	@Bean
	public OrderEventPersistenceService orderEventPersistenceService(final OrderEventRepository eventRepository) {
		return new OrderEventPersistenceService(eventRepository);
	}

	@Bean
	public AccountPersistenceService accountPersistenceService(final AccountRepository accountRepository,
			final OrderEventRepository orderEventRepository, final InstrumentApi instrumentApi) {
		return new AccountPersistenceService(accountRepository, orderEventRepository, instrumentApi);
	}
}
