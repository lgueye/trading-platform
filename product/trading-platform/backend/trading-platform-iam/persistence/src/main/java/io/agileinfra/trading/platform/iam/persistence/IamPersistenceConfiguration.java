package io.agileinfra.trading.platform.iam.persistence;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.iam.configuration.server.JwtProvider;
import io.agileinfra.trading.platform.iam.configuration.server.TradingPlatformSecurityServerConfiguration;
import io.agileinfra.trading.platform.persistence.configuration.TradingPlatformPersistenceConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"io.agileinfra.trading.platform.iam.persistence"})
@EnableJpaRepositories(basePackages = {"io.agileinfra.trading.platform.iam.persistence"})
@Configuration
@Import({TradingPlatformPersistenceConfiguration.class, TradingPlatformSecurityServerConfiguration.class})
public class IamPersistenceConfiguration {
	@Bean
	public IamPersistenceService iamPersistenceService(final UserRepository repository, UserRoleRepository roleRepository,
			final JwtProvider jwtProvider, final ClockApi clockApi) {
		return new IamPersistenceService(repository, roleRepository, jwtProvider, clockApi);
	}

}
