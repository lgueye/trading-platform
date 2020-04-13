package io.agileinfra.trading.platform.messaging.consumer.price;

import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.consumer.configuration.TradingPlatformMessagingConsumerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Import({TradingPlatformMessagingConsumerConfiguration.class, ClockApiConfiguration.class})
@Configuration
public class PriceEventConsumerConfiguration {
	/**
	 *
	 * @param clockApi
	 * @param priceEventReactor
	 * @return
	 */
	@Bean
	public PriceEventConsumer priceEventConsumer(final ClockApi clockApi, final PriceEventReactor priceEventReactor) {
		return new PriceEventConsumer(clockApi, priceEventReactor);
	}
}
