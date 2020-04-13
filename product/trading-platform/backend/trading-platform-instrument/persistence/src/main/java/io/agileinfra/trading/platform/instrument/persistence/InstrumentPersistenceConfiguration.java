package io.agileinfra.trading.platform.instrument.persistence;

import io.agileinfra.trading.platform.persistence.configuration.TradingPlatformPersistenceConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"io.agileinfra.trading.platform.instrument.persistence"})
@EnableJpaRepositories(basePackages = {"io.agileinfra.trading.platform.instrument.persistence"})
@Configuration
@Import(TradingPlatformPersistenceConfiguration.class)
public class InstrumentPersistenceConfiguration {
	@Bean
	public InstrumentPersistenceService instrumentPersistenceService(final InstrumentRepository repository) {
		return new InstrumentPersistenceService(repository);
	}

	@Bean
	public InstrumentEventPersistenceService instrumentEventPersistenceService(final InstrumentEventRepository repository) {
		return new InstrumentEventPersistenceService(repository);
	}
}
