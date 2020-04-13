package io.agileinfra.trading.platform.persistence.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class TradingPlatformPersistenceServerConfiguration {

	@Bean
	public H2Server h2Server(@Value("${persistence.server.port}") final Integer port, @Value("${persistence.server.base-dir}") final String baseDir) {
		return new H2Server(port, baseDir);
	}

}
