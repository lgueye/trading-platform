package io.agileinfra.trading.platform.persistence.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author louis.gueye@gmail.com
 */
@EnableAutoConfiguration
@Configuration
@EntityScan(basePackages = {"io.agileinfra.trading.platform.persistence"})
@EnableTransactionManagement
public class TradingPlatformPersistenceConfiguration {
}
