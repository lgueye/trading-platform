package io.agileinfra.trading.platform.e2e;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.agileinfra.trading.platform.account.api.AccountApiConfiguration;
import io.agileinfra.trading.platform.account.api.AccountNotificationApiConfiguration;
import io.agileinfra.trading.platform.booking.api.BookingApiConfiguration;
import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.iam.api.IamApiConfiguration;
import io.agileinfra.trading.platform.instrument.api.InstrumentApiConfiguration;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * @author louis.gueye@gmail.com
 */
@Import({OrderEventProducerConfiguration.class, PriceEventProducerConfiguration.class, BookingEventProducerConfiguration.class,
		AccountApiConfiguration.class, BookingApiConfiguration.class, ClockApiConfiguration.class, InstrumentApiConfiguration.class,
		IamApiConfiguration.class, AccountNotificationApiConfiguration.class})
@Configuration
public class TradingPlatformE2EConfiguration {

	@Bean
	public DataSource dataSource(@Value("${persistence.server.url}") final String url, @Value("${persistence.server.user}") final String username,
			@Value("${persistence.server.password}") final String password, @Value("${persistence.server.schema}") final String schema,
			@Value("${persistence.client.driver}") final String driverClassName) {
		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setPoolName("e2e-hikari");
		hikariConfig.setConnectionTestQuery("select 1");
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setSchema(schema);
		return new HikariDataSource(hikariConfig);
	}
}
